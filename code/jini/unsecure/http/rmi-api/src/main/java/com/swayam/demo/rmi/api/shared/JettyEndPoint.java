package com.swayam.demo.rmi.api.shared;

import java.io.IOException;
import java.io.Serializable;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.jeri.Endpoint;
import net.jini.jeri.OutboundRequest;
import net.jini.jeri.OutboundRequestIterator;

public class JettyEndPoint implements Endpoint, Serializable {

    private static final long serialVersionUID = 1L;

    private final String host;
    private final int port;

    public JettyEndPoint(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public OutboundRequestIterator newRequest(InvocationConstraints constraints) {
        return new OutboundRequestIterator() {

            private boolean dispatched = false;

            @Override
            public boolean hasNext() {
                return !dispatched;
            }

            @Override
            public OutboundRequest next() throws IOException {
                dispatched = true;
                return new HttpOutboundRequest(host, port);
            }
        };
    }

}
