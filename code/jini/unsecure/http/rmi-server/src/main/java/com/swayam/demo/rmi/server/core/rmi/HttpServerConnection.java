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

package com.swayam.demo.rmi.server.core.rmi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.security.AccessController;
import java.security.PrivilegedAction;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.RequestDispatcher;

import com.sun.jini.jeri.internal.http.TimedConnection;
import com.sun.jini.thread.Executor;
import com.sun.jini.thread.GetThreadPoolAction;
import com.swayam.demo.rmi.api.shared.Header;
import com.swayam.demo.rmi.api.shared.MessageReader;
import com.swayam.demo.rmi.api.shared.MessageWriter;
import com.swayam.demo.rmi.api.shared.StartLine;

/**
 * Class representing a server-side HTTP connection used to receive and dispatch
 * incoming HTTP requests.
 *
 * @author Sun Microsystems, Inc.
 * 
 */
public class HttpServerConnection implements TimedConnection {

    private static final int HTTP_MAJOR = 1;
    private static final int HTTP_MINOR = 1;

    private static final int UNSTARTED = 0;
    private static final int IDLE = 1;
    private static final int BUSY = 2;
    private static final int CLOSED = 3;

    private static final String serverString = AccessController.doPrivileged(new PrivilegedAction<String>() {
        public String run() {
            return "Java/" + System.getProperty("java.version", "???") + " " + HttpServerConnection.class.getName();
        }
    });

    private static final Executor userThreadPool = (Executor) java.security.AccessController.doPrivileged(new GetThreadPoolAction(true));

    private final InputStream in;
    private final OutputStream out;
    private final RequestDispatcher dispatcher;
    private final Object stateLock = new Object();
    private int state = UNSTARTED;

    /**
     * Creates new HttpServerConnection on top of given socket.
     */
    public HttpServerConnection(Socket sock, RequestDispatcher dispatcher) throws IOException {
        if (dispatcher == null) {
            throw new NullPointerException();
        }
        this.dispatcher = dispatcher;
        in = new BufferedInputStream(sock.getInputStream());
        out = new BufferedOutputStream(sock.getOutputStream());
        start();
    }

    public boolean shutdown(boolean force) {
        return true;
    }

    protected void checkPermissions() {

    }

    protected InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        return InvocationConstraints.EMPTY;
    }

    /**
     * Starts request dispatch thread. Throws IllegalStateException if
     * connection has already been started, or is closed.
     */
    protected void start() {
        synchronized (stateLock) {
            if (state != UNSTARTED) {
                throw new IllegalStateException();
            }
            state = IDLE;
            userThreadPool.execute(new Dispatcher(), "HTTP dispatcher");
        }
    }

    /**
     * Incoming request dispatcher.
     */
    private class Dispatcher implements Runnable {

        /**
         * Dispatch loop.
         */
        public void run() {
            try {
                for (;;) {

                    MessageReader reader = new MessageReader(in, false);
                    StartLine sline = reader.readStartLine();

                    synchronized (stateLock) {
                        if (state == CLOSED) {
                            return;
                        }
                        state = BUSY;
                    }

                    Header header = reader.readHeader();
                    String reqType = header.getField("RMI-Request-Type");
                    if (!"POST".equals(sline.method)) {
                        handleBadRequest(sline, header, reader);
                    } else if ("standard".equalsIgnoreCase(reqType)) {
                        handleRequest(sline, header, reader);
                    } else if ("ping".equalsIgnoreCase(reqType)) {
                        handlePing(sline, header, reader);
                    } else {
                        handleBadRequest(sline, header, reader);
                    }

                    synchronized (stateLock) {
                        if (state == CLOSED) {
                            return;
                        }
                        state = IDLE;
                    }
                }
            } catch (IOException ex) {
            } finally {
                shutdown(true);
            }
        }

        /**
         * Handles unacceptable HTTP request.
         */
        private void handleBadRequest(StartLine inLine, Header inHeader, MessageReader reader) throws IOException {
            inHeader.merge(reader.readTrailer());
            boolean persist = supportsPersist(inLine, inHeader);

            MessageWriter writer = new MessageWriter(out, false);
            writer.writeStartLine(new StartLine(HTTP_MAJOR, HTTP_MINOR, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request"));
            writer.writeHeader(createResponseHeader(persist));
            writer.writeTrailer(null);

            if (!persist) {
                shutdown(true);
            }
        }

        /**
         * Handles ping request.
         */
        private void handlePing(StartLine inLine, Header inHeader, MessageReader reader) throws IOException {
            inHeader.merge(reader.readTrailer());
            boolean persist = supportsPersist(inLine, inHeader);

            MessageWriter writer = new MessageWriter(out, false);
            writer.writeStartLine(new StartLine(HTTP_MAJOR, HTTP_MINOR, HttpURLConnection.HTTP_OK, "OK"));
            writer.writeHeader(createResponseHeader(persist));
            writer.writeTrailer(null);

            if (!persist) {
                shutdown(true);
            }
        }

        /**
         * Handles "standard" (i.e., dispatchable) request.
         */
        private void handleRequest(StartLine inLine, Header inHeader, MessageReader reader) throws IOException {
            boolean persist = supportsPersist(inLine, inHeader);
            boolean chunk = supportsChunking(inLine, inHeader);

            MessageWriter writer = new MessageWriter(out, chunk);
            writer.writeStartLine(new StartLine(HTTP_MAJOR, HTTP_MINOR, HttpURLConnection.HTTP_OK, "OK"));
            writer.writeHeader(createResponseHeader(persist));

            InboundRequestImpl req = new InboundRequestImpl(reader, writer);
            try {
                dispatcher.dispatch(req);
            } catch (Throwable th) {
            }
            req.finish();

            if (!persist || req.streamCorrupt()) {
                shutdown(true);
            }
        }
    }

    /**
     * Returns true if the received message start line and header indicate that
     * the connection can be persisted.
     */
    private static boolean supportsPersist(StartLine sline, Header header) {
        if (header.containsValue("Connection", "close", true)) {
            return false;
        } else if (header.containsValue("Connection", "Keep-Alive", true)) {
            return true;
        } else {
            int c = StartLine.compareVersions(sline.major, sline.minor, 1, 1);
            return c >= 0;
        }
    }

    /**
     * Returns true if the received message start line indicates that the sender
     * understands chunked transfer coding.
     */
    private static boolean supportsChunking(StartLine sline, Header header) {
        int c = StartLine.compareVersions(sline.major, sline.minor, 1, 1);
        // REMIND: is requiring "TE: trailers" too strict?
        return c >= 0 && header.containsValue("TE", "trailers", true);
    }

    /**
     * Creates base header to be used in response message. If persist is true,
     * adds fields indicating a persistent connection.
     */
    private static Header createResponseHeader(boolean persist) {
        Header header = new Header();
        long now = System.currentTimeMillis();
        header.setField("Date", Header.getDateString(now));
        header.setField("Server", serverString);
        header.setField("Connection", persist ? "Keep-Alive" : "close");
        return header;
    }
}
