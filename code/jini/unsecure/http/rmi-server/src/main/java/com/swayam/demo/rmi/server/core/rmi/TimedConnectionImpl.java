package com.swayam.demo.rmi.server.core.rmi;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.RequestDispatcher;

import com.sun.jini.jeri.internal.http.HttpServerConnection;
import com.sun.jini.jeri.internal.runtime.Util;
import com.swayam.demo.rmi.api.shared.Constraints;

/**
 * HttpServerConnection subclass.
 **/
class TimedConnectionImpl extends HttpServerConnection {

    private static final Logger logger = Logger.getLogger("net.jini.jeri.http.server");

    private final Socket socket;
    private final Object connLock = new Object();
    private final Object lock;
    private boolean connClosed;
    private final boolean closed;
    private final Set conns;

    TimedConnectionImpl(Object lock, RequestDispatcher requestDispatcher, boolean closed, Set conns, Socket socket) throws IOException {
        super(socket, requestDispatcher, JettyServerEndpoint2.serverManager);
        this.socket = socket;
        this.lock = lock;
        this.closed = closed;
        this.conns = conns;

        boolean needShutdown = false;
        synchronized (lock) {
            if (closed) {
                needShutdown = true; // shutdown after releasing lock
            } else {
                conns.add(this);
            }
        }
        if (needShutdown) {
            shutdown(true);
        } else {
            start();
        }
    }

    public boolean shutdown(boolean force) {
        synchronized (connLock) {
            if (connClosed) {
                return true;
            }
            connClosed = super.shutdown(force);
            if (!connClosed) {
                return false;
            }
        }

        JettyServerEndpoint2.connTimer.cancelTimeout(this);
        synchronized (lock) {
            if (!closed) { // must not mutate set after closed
                conns.remove(this);
            }
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "shut down connection on socket {0}", socket);
        }
        return true;
    }

    protected void checkPermissions() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkAccept(socket.getInetAddress().getHostAddress(), socket.getPort());
        }
    }

    protected InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        /*
         * The transport layer aspects of all constraints supported by this
         * transport provider are always satisfied by all requests on the server
         * side, so this method can have the same static behavior as
         * ServerCapabilities.checkConstraints does. (Otherwise, this operation
         * would need to be parameterized by this connection or the request.)
         */
        return Constraints.check(constraints, true);
    }

    /**
     * Populates the context collection with information representing the
     * connection.
     **/
    protected void populateContext(Collection context) {
        Util.populateContext(context, socket.getInetAddress());
    }

    protected void idle() {
        JettyServerEndpoint2.connTimer.scheduleTimeout(this, false);
    }

    protected void busy() {
        JettyServerEndpoint2.connTimer.cancelTimeout(this);
    }
}