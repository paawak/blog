package com.swayam.demo.rmi.server.core.jini;

import java.io.IOException;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.Endpoint;
import net.jini.jeri.ServerEndpoint;
import net.jini.jeri.http.HttpServerEndpoint;

import com.swayam.demo.rmi.api.shared.ServletBasedEndpoint;

public class ServletBasedServerEndpoint implements ServerEndpoint {

    private final HttpServerEndpoint httpServerEndpoint;
    private final String host;
    private final int port;

    public ServletBasedServerEndpoint(String host, int port) {
        httpServerEndpoint = HttpServerEndpoint.getInstance(port);
        this.host = host;
        this.port = port;
    }

    @Override
    public InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        return httpServerEndpoint.checkConstraints(constraints);
    }

    @Override
    public Endpoint enumerateListenEndpoints(ListenContext listenContext) throws IOException {
        return new ServletBasedEndpoint(host, port);
    }
}
