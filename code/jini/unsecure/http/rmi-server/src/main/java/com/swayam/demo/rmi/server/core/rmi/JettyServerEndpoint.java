package com.swayam.demo.rmi.server.core.rmi;

import java.io.IOException;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.Endpoint;
import net.jini.jeri.RequestDispatcher;
import net.jini.jeri.ServerEndpoint;
import net.jini.jeri.http.HttpServerEndpoint;

import com.swayam.demo.rmi.api.shared.JettyEndPoint;

public class JettyServerEndpoint implements ServerEndpoint {

    private final HttpServerEndpoint httpServerEndpoint;
    private final String host;
    private final int port;

    public JettyServerEndpoint(String host, int port) {
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

        ListenCookie listenCookie = listenContext.addListenEndpoint(new ListenEndpoint() {

            @Override
            public ListenHandle listen(RequestDispatcher requestDispatcher) throws IOException {

                ListenHandle listenHandle = new ListenHandle() {

                    @Override
                    public ListenCookie getCookie() {
                        return new HttpCookie("http://localhost:8100");
                    }

                    @Override
                    public void close() {

                    }
                };

                // requestDispatcher.dispatch(new
                // HttpInboundRequest("http://localhost:8100"));

                return listenHandle;
            }

            @Override
            public void checkPermissions() {

            }
        });

        return new JettyEndPoint(host, port);
    }

    private static class HttpCookie implements ListenCookie {
        private final String httpServer;

        public HttpCookie(String httpServer) {
            this.httpServer = httpServer;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((httpServer == null) ? 0 : httpServer.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            HttpCookie other = (HttpCookie) obj;
            if (httpServer == null) {
                if (other.httpServer != null)
                    return false;
            } else if (!httpServer.equals(other.httpServer))
                return false;
            return true;
        }
    }

}
