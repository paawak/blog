package com.swayam.demo.rmi.server.core.rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.jini.jeri.RequestDispatcher;
import net.jini.jeri.ServerEndpoint.ListenCookie;
import net.jini.jeri.ServerEndpoint.ListenHandle;
import net.jini.security.SecurityContext;

/**
 * ListenHandle implementation: represents a listen operation.
 **/
class ListenHandleImpl implements ListenHandle {

    private static final Logger logger = Logger.getLogger("net.jini.jeri.http.server");

    private final RequestDispatcher requestDispatcher;
    private final ServerSocket serverSocket;
    private final SecurityContext context;
    private final ListenCookie cookie;

    private final Object lock = new Object();
    private boolean closed = false;

    ListenHandleImpl(RequestDispatcher requestDispatcher, ServerSocket serverSocket, SecurityContext context, ListenCookie cookie) {
        this.requestDispatcher = requestDispatcher;
        this.serverSocket = serverSocket;
        this.context = context;
        this.cookie = cookie;
    }

    /**
     * Starts the accept loop.
     **/
    void startAccepting() {
        JettyServerEndpoint2.systemThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    executeAcceptLoop();
                } finally {
                    /*
                     * The accept loop is only started once, so after no more
                     * socket accepts will occur, ensure that the server socket
                     * is no longer listening.
                     */
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                    }
                }
            }
        }, toString() + " accept loop");
    }

    /**
     * Runs the accept loop in the access control context preserved by
     * LE.listen.
     **/
    private void executeAcceptLoop() {
        AccessController.doPrivileged(context.wrap(new PrivilegedAction() {
            public Object run() {
                executeAcceptLoop0();
                return null;
            }
        }), context.getAccessControlContext());
    }

    /**
     * Executes the accept loop.
     **/
    private void executeAcceptLoop0() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "accepted socket {0} from server socket {1}", new Object[] { socket, serverSocket });
            }

            JettyServerEndpoint2.setSocketOptions(socket);

            try {
                new HttpServerConnection(socket, requestDispatcher);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Stops this listen operation.
     **/
    public void close() {
        synchronized (lock) {
            if (closed) {
                return;
            }
            closed = true;
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
        }

    }

    /**
     * Returns a cookie to identify this listen operation.
     **/
    public ListenCookie getCookie() {
        return cookie;
    }

    public String toString() {
        return "HttpServerEndpoint.LH[" + serverSocket + "]";
    }

}