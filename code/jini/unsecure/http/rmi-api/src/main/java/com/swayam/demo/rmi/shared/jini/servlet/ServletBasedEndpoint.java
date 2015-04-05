package com.swayam.demo.rmi.shared.jini.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.NoSuchElementException;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.jeri.Endpoint;
import net.jini.jeri.OutboundRequest;
import net.jini.jeri.OutboundRequestIterator;

public class ServletBasedEndpoint implements Endpoint, Serializable {

    private static final long serialVersionUID = 1L;

    private final String host;
    private final int port;

    public ServletBasedEndpoint(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public OutboundRequestIterator newRequest(InvocationConstraints constraints) {
        if (constraints == null) {
            throw new NullPointerException();
        }
        return new OutboundRequestIterator() {
            private boolean nextCalled = false;
            private OutboundRequest currentRequest;

            public OutboundRequest next() throws IOException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                nextCalled = true;
                currentRequest = nextRequest(constraints);
                return currentRequest;
            }

            public boolean hasNext() {
                // NYI: determine if HTTP failure suggests retry
                return !nextCalled;
            }
        };
    }

    private OutboundRequest nextRequest(InvocationConstraints constraints) throws IOException {

        final OutboundRequest request = nextRequest();

        // must wrap to provide getUnfulfilledConstraints implementation
        return new OutboundRequest() {
            public void populateContext(Collection context) {
                request.populateContext(context);
            }

            public InvocationConstraints getUnfulfilledConstraints() {
                return InvocationConstraints.EMPTY;
            }

            public OutputStream getRequestOutputStream() {
                return request.getRequestOutputStream();
            }

            public InputStream getResponseInputStream() {
                return request.getResponseInputStream();
            }

            public boolean getDeliveryStatus() {
                return request.getDeliveryStatus();
            }

            public void abort() {
                request.abort();
            }
        };
    }

    private OutboundRequest nextRequest() throws IOException {
        return new ServletOutboundRequest(host, port);
    }

}
