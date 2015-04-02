package com.swayam.demo.rmi.server.core.rmi;

import java.io.IOException;
import java.net.ServerSocket;

import net.jini.jeri.RequestDispatcher;
import net.jini.jeri.ServerEndpoint.ListenCookie;
import net.jini.jeri.ServerEndpoint.ListenEndpoint;
import net.jini.jeri.ServerEndpoint.ListenHandle;
import net.jini.security.Security;

/**
 * ListenEndpoint implementation.
 **/
class ListenEndpointImpl implements ListenEndpoint {

    private final int port;

    ListenEndpointImpl(int port) {
        this.port = port;
    }

    public void checkPermissions() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkListen(port);
        }
    }

    public ListenHandle listen(RequestDispatcher requestDispatcher) throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);

        Cookie cookie = new Cookie(serverSocket.getLocalPort());
        ListenHandleImpl listenHandle = new ListenHandleImpl(requestDispatcher, serverSocket, Security.getContext(), cookie);
        listenHandle.startAccepting();
        return listenHandle;
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
            return "ListenEndpointImpl.Cookie[" + port + "]";
        }
    }
}