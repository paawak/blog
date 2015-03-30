/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.swayam.demo.rmi.api.shared;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.context.AcknowledgmentSource;
import net.jini.jeri.OutboundRequest;

import com.sun.jini.jeri.internal.http.HttpClientSocketFactory;
import com.sun.jini.jeri.internal.http.HttpParseException;

/**
 * Class representing a client-side HTTP connection used to send HTTP requests.
 *
 * @author Sun Microsystems, Inc.
 * 
 */
public class JettyClientConnection {

    private static final int HTTP_MAJOR = 1;
    private static final int HTTP_MINOR = 1;

    private static final String clientString = (String) AccessController.doPrivileged(new PrivilegedAction() {
        public Object run() {
            return "Java/" + System.getProperty("java.version", "???") + " " + JettyClientConnection.class.getName();
        }
    });

    /* modes */
    private static final int DIRECT = 0;
    private static final int PROXIED = 1;
    private static final int TUNNELED = 2;

    private final int mode;

    private final HttpClientManager manager;
    private ServerInfo targetInfo;
    private ServerInfo proxyInfo;
    private final boolean persist;
    private String[] acks;

    private Socket sock;
    private OutputStream out;
    private InputStream in;

    private final HttpClientSocketFactory factory;

    /**
     * Creates HttpClientConnection which sends requests directly to given
     * host/port through a socket obtained from the given socket factory. The
     * createSocket method of the given socket factory may be called more than
     * once in cases where connection establishment involves multiple HTTP
     * message exchanges.
     */
    public JettyClientConnection(String host, int port, HttpClientSocketFactory factory, HttpClientManager manager) throws IOException {
        this.manager = manager;
        mode = DIRECT;
        targetInfo = manager.getServerInfo(host, port);
        persist = true;
        this.factory = factory;
    }

    /**
     * Initiates new request to connection target. Throws an IOException if the
     * connection is currently busy.
     */
    public OutboundRequest newRequest() throws IOException {
        return new OutboundRequestImpl();
    }

    /**
     * Flushes current copy of server/proxy HTTP information to cache.
     */
    private void flushServerInfo() {
        manager.cacheServerInfo(targetInfo);
        if (mode != DIRECT) {
            manager.cacheServerInfo(proxyInfo);
        }
    }

    /**
     * Establishes connection using sockets from the given socket factory.
     * Throws IOException if connection setup fails.
     */
    private void setupConnection(HttpClientSocketFactory factory) throws IOException {
        boolean ok = false;
        try {
            /*
             * 4 cycles required in worst-case (proxied) scenario: i = 0: send
             * OPTIONS request to proxy i = 1: send ping, fails with 407 (proxy
             * auth required) i = 2: send ping, fails with 401 (unauthorized) i
             * = 3: return
             */
            for (int i = 0; i < 4; i++) {
                if (sock == null) {
                    connect(factory);
                }
                if (targetInfo.timestamp == ServerInfo.NO_TIMESTAMP) {
                    ping(true);
                } else {
                    ok = true;
                    return;
                }
            }
        } finally {
            if (!ok) {
                disconnect();
            }
        }
        throw new ConnectException("failed to establish HTTP connection");
    }

    /**
     * Opens underlying connection. If tunneling through an HTTP proxy, attempts
     * CONNECT request.
     */
    private void connect(HttpClientSocketFactory factory) throws IOException {
        disconnect();
        for (int i = 0; i < 2; i++) {
            if (sock == null) {
                ServerInfo sinfo = (mode == DIRECT) ? targetInfo : proxyInfo;
                sock = factory.createSocket(sinfo.host, sinfo.port);
                out = new BufferedOutputStream(sock.getOutputStream());
                in = new BufferedInputStream(sock.getInputStream());
            }
            if (mode != TUNNELED) {
                return;
            }
        }
        throw new ConnectException("failed to establish proxy tunnel");
    }

    /**
     * Closes and releases reference to underlying socket.
     */
    private void disconnect() {
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException ex) {
            }
            sock = null;
            out = null;
            in = null;
        }
    }

    /**
     * Pings target. Returns true if succeeded, false if failed "cleanly".
     */
    private boolean ping(boolean setup) throws IOException {
        StartLine outLine = createPostLine();
        Header outHeader = createPostHeader(outLine);
        outHeader.setField("RMI-Request-Type", "ping");
        MessageWriter writer = new MessageWriter(out, false);

        writer.writeStartLine(outLine);
        writer.writeHeader(outHeader);
        writer.writeTrailer(null);

        MessageReader reader;
        StartLine inLine;
        Header inHeader;
        do {
            reader = new MessageReader(in, false);
            inLine = reader.readStartLine();
            inHeader = reader.readHeader();
            inHeader.merge(reader.readTrailer());
        } while (inLine.status / 100 == 1);

        analyzePostResponse(inLine, inHeader);
        if (!supportsPersist(inLine, inHeader)) {
            if (setup) {
                disconnect();
            }
        }
        return (inLine.status / 100) == 2;
    }

    /**
     * Creates start line for outbound HTTP POST message.
     */
    private StartLine createPostLine() {
        String uri = (mode == PROXIED) ? "http://" + targetInfo.host + ":" + targetInfo.port + "/" : "/";
        return new StartLine(HTTP_MAJOR, HTTP_MINOR, "POST", uri);
    }

    /**
     * Creates base header containing fields common to all HTTP messages sent by
     * this connection.
     */
    private Header createBaseHeader() {
        Header header = new Header();
        long now = System.currentTimeMillis();
        header.setField("Date", Header.getDateString(now));
        header.setField("User-Agent", clientString);
        return header;
    }

    /**
     * Creates header for outbound HTTP POST message with given start line.
     */
    private Header createPostHeader(StartLine sline) {
        Header header = createBaseHeader();
        header.setField("Host", targetInfo.host + ":" + targetInfo.port);
        header.setField("Connection", persist ? "TE" : "close, TE");
        header.setField("TE", "trailers");

        // REMIND: eliminate hardcoded protocol string
        String auth = targetInfo.getAuthString("http", sline.method, sline.uri);
        if (auth != null) {
            header.setField("Authorization", auth);
        }
        if (mode == PROXIED) {
            auth = proxyInfo.getAuthString("http", sline.method, sline.uri);
            if (auth != null) {
                header.setField("Proxy-Authorization", auth);
            }
        }

        acks = manager.getUnsentAcks(targetInfo.host, targetInfo.port);
        if (acks.length > 0) {
            String ackList = acks[0];
            for (int i = 1; i < acks.length; i++) {
                ackList += ", " + acks[i];
            }
            header.setField("RMI-Response-Ack", ackList);
        }
        return header;
    }

    /**
     * Analyzes POST response message start line and header, updating cached
     * target/proxy server information if necessary.
     */
    private void analyzePostResponse(StartLine inLine, Header inHeader) {
        String str;
        long now = System.currentTimeMillis();

        if ((str = inHeader.getField("WWW-Authenticate")) != null) {
            try {
                targetInfo.setAuthInfo(str);
            } catch (HttpParseException ex) {
            }
            targetInfo.timestamp = now;
        } else if ((str = inHeader.getField("Authentication-Info")) != null) {
            try {
                targetInfo.updateAuthInfo(str);
            } catch (HttpParseException ex) {
            }
            targetInfo.timestamp = now;
        }

        if (mode != DIRECT) {
            if ((str = inHeader.getField("Proxy-Authenticate")) != null) {
                try {
                    proxyInfo.setAuthInfo(str);
                } catch (HttpParseException ex) {
                }
                proxyInfo.timestamp = now;
            } else if ((str = inHeader.getField("Proxy-Authentication-Info")) != null) {
                try {
                    proxyInfo.updateAuthInfo(str);
                } catch (HttpParseException ex) {
                }
                proxyInfo.timestamp = now;
            }
        }

        if (mode != PROXIED) {
            targetInfo.major = inLine.major;
            targetInfo.minor = inLine.minor;
            targetInfo.timestamp = now;
        } else {
            /*
             * Return message was sent by proxy; however, since some proxies
             * incorrectly relay the target server's version numbers instead of
             * their own, we can only rely on version numbers which could not
             * have been sent from target server.
             */
            if (inLine.status == HttpURLConnection.HTTP_PROXY_AUTH) {
                proxyInfo.major = inLine.major;
                proxyInfo.minor = inLine.minor;
            }
            proxyInfo.timestamp = now;
        }

        if ((inLine.status / 100) == 2) {
            manager.clearUnsentAcks(targetInfo.host, targetInfo.port, acks);
            targetInfo.timestamp = now;
        }

        flushServerInfo();
    }

    /**
     * Returns true if requests sent over this connection should chunk output.
     */
    private boolean supportsChunking() {
        ServerInfo si = (mode == PROXIED) ? proxyInfo : targetInfo;
        return StartLine.compareVersions(si.major, si.minor, 1, 1) >= 0;
    }

    /**
     * Returns true if the given response line and header indicate that the
     * connection can be persisted, and use of persistent connections has not
     * been disabled.
     */
    private boolean supportsPersist(StartLine sline, Header header) {
        if (header.containsValue("Connection", "close", true)) {
            return false;
        } else if (!persist) {
            return false;
        } else if (header.containsValue("Connection", "Keep-Alive", true)) {
            return true;
        } else {
            int c = StartLine.compareVersions(sline.major, sline.minor, 1, 1);
            return c >= 0;
        }
    }

    /**
     * HTTP-based implementation of OutboundRequest abstraction.
     */
    private class OutboundRequestImpl extends Request implements OutboundRequest {
        private final MessageWriter writer;
        private MessageReader reader;
        private StartLine inLine;
        private Header inHeader;

        OutboundRequestImpl() throws IOException {
            setupConnection(factory);
            StartLine outLine = createPostLine();
            Header outHeader = createPostHeader(outLine);
            outHeader.setField("RMI-Request-Type", "standard");

            writer = new MessageWriter(out, supportsChunking());
            writer.writeStartLine(outLine);
            writer.writeHeader(outHeader);
            writer.flush();
        }

        public void populateContext(Collection context) {
            if (context == null) {
                throw new NullPointerException();
            }
        }

        public InvocationConstraints getUnfulfilledConstraints() {
            return InvocationConstraints.EMPTY;
        }

        public OutputStream getRequestOutputStream() {
            return getOutputStream();
        }

        public InputStream getResponseInputStream() {
            return getInputStream();
        }

        void startOutput() throws IOException {
            // start line, header already written
        }

        void write(byte[] b, int off, int len) throws IOException {
            writer.writeContent(b, off, len);
        }

        void endOutput() throws IOException {
            writer.writeTrailer(null);
        }

        boolean startInput() throws IOException {
            for (;;) {
                reader = new MessageReader(in, false);
                inLine = reader.readStartLine();
                inHeader = reader.readHeader();
                if (inLine.status / 100 != 1) {
                    return inLine.status / 100 == 2;
                }
                reader.readTrailer();
            }
        }

        int read(byte[] b, int off, int len) throws IOException {
            return reader.readContent(b, off, len);
        }

        int available() throws IOException {
            return reader.availableContent();
        }

        void endInput() throws IOException {
            inHeader.merge(reader.readTrailer());
            analyzePostResponse(inLine, inHeader);
        }

        void addAckListener(AcknowledgmentSource.Listener listener) {
            throw new UnsupportedOperationException();
        }

        void done(boolean corrupt) {

        }
    }
}
