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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.context.AcknowledgmentSource;
import net.jini.jeri.OutboundRequest;

/**
 * Class representing a client-side HTTP connection used to send HTTP requests.
 *
 * @author Sun Microsystems, Inc.
 * 
 */
public class OutboundRequestImpl extends Request implements OutboundRequest {

    private static final int HTTP_MAJOR = 1;
    private static final int HTTP_MINOR = 1;

    private static final String clientString = AccessController.doPrivileged(new PrivilegedAction<String>() {
        @Override
        public String run() {
            return "Java/" + System.getProperty("java.version", "???") + " " + OutboundRequestImpl.class.getName();
        }
    });

    private final boolean persist;

    private final MessageWriter writer;

    private final String host;

    private final int port;

    private final MessageReader reader;

    private Header inHeader;

    /**
     * Creates HttpClientConnection which sends requests directly to given
     * host/port through a socket obtained from the given socket factory. The
     * createSocket method of the given socket factory may be called more than
     * once in cases where connection establishment involves multiple HTTP
     * message exchanges.
     */
    public OutboundRequestImpl(String host, int port, IOStreamProvider ioStreamProvider) throws IOException {
        persist = true;
        this.host = host;
        this.port = port;

        reader = new MessageReader(ioStreamProvider.getInputStream(), false);

        StartLine outLine = createPostLine();
        Header outHeader = createPostHeader(outLine);
        outHeader.setField("RMI-Request-Type", "standard");

        writer = new MessageWriter(ioStreamProvider.getOutputStream(), false);
        writer.writeStartLine(outLine);
        writer.writeHeader(outHeader);
        writer.flush();
    }

    /**
     * Creates start line for outbound HTTP POST message.
     */
    private StartLine createPostLine() {
        String uri = "/";
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
        header.setField("Host", host + ":" + port);
        header.setField("Connection", persist ? "TE" : "close, TE");
        header.setField("TE", "trailers");

        return header;
    }

    @Override
    public void populateContext(@SuppressWarnings("rawtypes") Collection context) {
        if (context == null) {
            throw new NullPointerException();
        }
    }

    @Override
    public InvocationConstraints getUnfulfilledConstraints() {
        return InvocationConstraints.EMPTY;
    }

    @Override
    public OutputStream getRequestOutputStream() {
        return getOutputStream();
    }

    @Override
    public InputStream getResponseInputStream() {
        return getInputStream();
    }

    void startOutput() throws IOException {
        // start line, header already written
    }

    @Override
    void write(byte[] b, int off, int len) throws IOException {
        writer.writeContent(b, off, len);
    }

    @Override
    void endOutput() throws IOException {
        writer.writeTrailer(null);
    }

    @Override
    boolean startInput() throws IOException {
        for (;;) {
            StartLine inLine = reader.readStartLine();
            inHeader = reader.readHeader();
            if (inLine.status / 100 != 1) {
                return inLine.status / 100 == 2;
            }
            reader.readTrailer();
        }
    }

    @Override
    int read(byte[] b, int off, int len) throws IOException {
        return reader.readContent(b, off, len);
    }

    @Override
    int available() throws IOException {
        return reader.availableContent();
    }

    @Override
    void endInput() throws IOException {
        inHeader.merge(reader.readTrailer());
    }

    @Override
    void addAckListener(AcknowledgmentSource.Listener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    void done(boolean corrupt) {

    }

}
