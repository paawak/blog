package com.swayam.demo.rmi.server.core.rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;

import javax.net.ServerSocketFactory;

import net.jini.jeri.RequestDispatcher;
import net.jini.jeri.ServerEndpoint.ListenCookie;
import net.jini.jeri.ServerEndpoint.ListenEndpoint;
import net.jini.jeri.ServerEndpoint.ListenHandle;
import net.jini.security.Security;

import com.sun.jini.jeri.internal.runtime.Util;

/**
 * ListenEndpoint implementation.
 **/
class ListenEndpointImpl implements ListenEndpoint {

    private final int port;

    private final ServerSocketFactory ssf;

    ListenEndpointImpl(int port, ServerSocketFactory ssf) {
        this.port = port;
        this.ssf = ssf;
    }

    public void checkPermissions() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkListen(port);
        }
    }

    public ListenHandle listen(RequestDispatcher requestDispatcher) throws IOException {
        if (requestDispatcher == null) {
            throw new NullPointerException();
        }

        ServerSocket serverSocket;
        if (ssf != null) {
            serverSocket = ssf.createServerSocket(port);
        } else {
            serverSocket = new ServerSocket(port);
        }

        if (JettyServerEndpoint2.logger.isLoggable(Level.FINE)) {
            JettyServerEndpoint2.logger.log(Level.FINE, (ssf == null ? "created server socket {0}" : "created server socket {0} using factory {1}"), new Object[] { serverSocket,
                    ssf });
        }

        Cookie cookie = new Cookie(serverSocket.getLocalPort());
        final ListenHandleImpl listenHandle = new ListenHandleImpl(requestDispatcher, serverSocket, Security.getContext(), cookie);
        listenHandle.startAccepting();
        return listenHandle;
    }

    // following are required to implement equals:
    private int getPort() {
        return port;
    }

    private ServerSocketFactory getSSF() {
        return ssf;
    }

    public int hashCode() {
        return port ^ (ssf != null ? ssf.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ListenEndpointImpl)) {
            return false;
        }
        ListenEndpointImpl other = (ListenEndpointImpl) obj;
        return port == other.getPort() && Util.sameClassAndEquals(ssf, other.getSSF());
    }

    public String toString() {
        return "HttpServerEndpoint.LE[" + port + (ssf != null ? "," + ssf : "") + "]";
    }

    /**
     * ListenCookie implementation: identifies a listen operation by containing
     * the local port that the server socket is bound to.
     **/
    private class Cookie implements ListenCookie {

        private final int port;

        Cookie(int port) {
            this.port = port;
        }

        public String toString() {
            return "HttpServerEndpoint.LE.Cookie[" + port + "]";
        }
    }
}