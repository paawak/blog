package com.swayam.demo.rmi.server.core.rmi;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.RequestDispatcher;

/**
 * HttpServerConnection subclass.
 **/
class TimedConnectionImpl extends HttpServerConnection {

    TimedConnectionImpl(RequestDispatcher requestDispatcher, Socket socket) throws IOException {
        super(socket, requestDispatcher, JettyServerEndpoint2.serverManager);
        start();
    }

    public boolean shutdown(boolean force) {
        return true;
    }

    @Override
    protected void checkPermissions() {

    }

    @Override
    protected InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        return InvocationConstraints.EMPTY;
    }

    @Override
    protected void populateContext(Collection context) {

    }

    @Override
    protected void idle() {

    }

    @Override
    protected void busy() {

    }
}