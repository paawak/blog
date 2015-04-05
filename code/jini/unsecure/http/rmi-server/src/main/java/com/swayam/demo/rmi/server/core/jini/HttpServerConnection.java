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

package com.swayam.demo.rmi.server.core.jini;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.jini.jeri.RequestDispatcher;

import com.swayam.demo.rmi.api.shared.Header;
import com.swayam.demo.rmi.api.shared.IOStreamProvider;
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
public class HttpServerConnection {

    private static final int HTTP_MAJOR = 1;
    private static final int HTTP_MINOR = 1;

    private static final String serverString = AccessController.doPrivileged(new PrivilegedAction<String>() {
        public String run() {
            return "Java/" + System.getProperty("java.version", "???") + " " + HttpServerConnection.class.getName();
        }
    });

    private final Executor threadPool;

    private final InputStream in;
    private final OutputStream out;
    private final RequestDispatcher dispatcher;

    /**
     * Creates new HttpServerConnection on top of given socket.
     */
    public HttpServerConnection(IOStreamProvider ioStreamProvider, RequestDispatcher dispatcher) throws IOException {
        if (dispatcher == null) {
            throw new NullPointerException();
        }
        this.dispatcher = dispatcher;
        in = new BufferedInputStream(ioStreamProvider.getInputStream());
        out = new BufferedOutputStream(ioStreamProvider.getOutputStream());

        threadPool = Executors.newFixedThreadPool(1);
    }

    /**
     * Starts request dispatch thread. Throws IllegalStateException if
     * connection has already been started, or is closed.
     */
    public void start() {
        threadPool.execute(new Dispatcher());
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

                }
            } catch (IOException ex) {
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
            dispatcher.dispatch(req);

            req.finish();

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
