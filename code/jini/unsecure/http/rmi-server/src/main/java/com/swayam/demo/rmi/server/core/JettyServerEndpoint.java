package com.swayam.demo.rmi.server.core;

import java.io.IOException;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.Endpoint;
import net.jini.jeri.ServerEndpoint;
import net.jini.jeri.http.HttpEndpoint;
import net.jini.jeri.http.HttpServerEndpoint;

public class JettyServerEndpoint implements ServerEndpoint {

    private final HttpServerEndpoint httpServerEndpoint;
    private final int port;

    public JettyServerEndpoint(int port) {
        httpServerEndpoint = HttpServerEndpoint.getInstance(port);
        this.port = port;
    }

    @Override
    public InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        return httpServerEndpoint.checkConstraints(constraints);
    }

    @Override
    public Endpoint enumerateListenEndpoints(ListenContext listenContext) throws IOException {
        return HttpEndpoint.getInstance("localhost", port);
    }

}
