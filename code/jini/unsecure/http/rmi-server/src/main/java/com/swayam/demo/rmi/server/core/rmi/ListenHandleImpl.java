package com.swayam.demo.rmi.server.core.rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.jini.jeri.RequestDispatcher;
import net.jini.jeri.ServerEndpoint.ListenCookie;
import net.jini.jeri.ServerEndpoint.ListenHandle;
import net.jini.security.SecurityContext;

import com.sun.jini.logging.LogUtil;

/**
 * ListenHandle implementation: represents a listen operation.
 **/
class ListenHandleImpl implements ListenHandle {

    private static final Logger logger = Logger.getLogger("net.jini.jeri.http.server");

    private final RequestDispatcher requestDispatcher;
    private final ServerSocket serverSocket;
    private final SecurityContext context;
    private final ListenCookie cookie;

    private long acceptFailureTime = 0L; // local to accept thread
    private int acceptFailureCount; // local to accept thread

    private final Object lock = new Object();
    private boolean closed = false;
    private final Set conns = new HashSet();

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
                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, "accepted socket {0} from server socket {1}", new Object[] { socket, serverSocket });
                }

                JettyServerEndpoint2.setSocketOptions(socket);

                new TimedConnectionImpl(lock, requestDispatcher, closed, conns, socket);

            } catch (Throwable t) {
                try {
                    /*
                     * If this listen operation has been stopped, then we expect
                     * the socket accept to throw an exception, so just
                     * terminate normally.
                     */
                    synchronized (lock) {
                        if (closed) {
                            break;
                        }
                    }

                    try {
                        if (logger.isLoggable(Level.WARNING)) {
                            LogUtil.logThrow(logger, Level.WARNING, JettyServerEndpoint2.class, "executeAcceptLoop", "accept loop for {0} throws", new Object[] { serverSocket }, t);
                        }
                    } catch (Throwable tt) {
                    }
                } finally {
                    /*
                     * Always close the accepted socket (if any) if an exception
                     * occurs, but only after logging an unexpected exception.
                     */
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                        }
                    }
                }

                if (!(t instanceof SecurityException)) {
                    try {
                        Object[] snapshot;
                        synchronized (lock) {
                            snapshot = closed ? null : conns.toArray();
                        }
                        if (snapshot != null) {
                            for (int i = 0; i < snapshot.length; i++) {
                                ((TimedConnectionImpl) snapshot[i]).shutdown(false);
                            }
                        }
                    } catch (OutOfMemoryError e) {
                    } catch (Exception e) {
                    }
                }

                /*
                 * A NoClassDefFoundError can occur if no file descriptors are
                 * available, in which case this loop should not terminate.
                 */
                boolean knownFailure = t instanceof Exception || t instanceof OutOfMemoryError || t instanceof NoClassDefFoundError;
                if (knownFailure) {
                    if (continueAfterAcceptFailure(t)) {
                        continue;
                    } else {
                        return;
                    }
                }

                throw (Error) t;
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
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "closed server socket {0}", serverSocket);
        }

        /*
         * Iterating over conns without synchronization is safe at this point
         * because no other thread will access it without verifying that closed
         * is false in a synchronized block first.
         */
        for (Iterator i = conns.iterator(); i.hasNext();) {
            ((TimedConnectionImpl) i.next()).shutdown(true);
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

    /**
     * Throttles the accept loop after ServerSocket.accept throws an exception,
     * and decides whether to continue at all. The current code is borrowed from
     * the JRMP implementation; it always continues, but it delays the loop
     * after bursts of failed accepts.
     **/
    private boolean continueAfterAcceptFailure(Throwable t) {
        /*
         * If we get a burst of NFAIL failures in NMSEC milliseconds, then wait
         * for ten seconds. This is to ensure that individual failures don't
         * cause hiccups, but sustained failures don't hog the CPU in futile
         * accept-fail-retry looping.
         */
        final int NFAIL = 10;
        final int NMSEC = 5000;
        long now = System.currentTimeMillis();
        if (acceptFailureTime == 0L || (now - acceptFailureTime) > NMSEC) {
            // failure time is very old, or this is first failure
            acceptFailureTime = now;
            acceptFailureCount = 0;
        } else {
            // failure window was started recently
            acceptFailureCount++;
            if (acceptFailureCount >= NFAIL) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignore) {
                }
                // no need to reset counter/timer
            }
        }
        return true;
    }
}