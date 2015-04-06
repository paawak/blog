package com.swayam.demo.rmi.server.core.jini.servlet;

import java.io.IOException;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.Endpoint;
import net.jini.jeri.RequestDispatcher;
import net.jini.jeri.ServerEndpoint;
import net.jini.jeri.http.HttpServerEndpoint;

import com.swayam.demo.rmi.shared.jini.servlet.ServletBasedEndpoint;
import com.swayam.demo.rmi.shared.jini.servlet.ServletInboundRequest;

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
        listenContext.addListenEndpoint(new ListenEndpoint() {

            @Override
            public ListenHandle listen(RequestDispatcher requestDispatcher) throws IOException {

                requestDispatcher.dispatch(new ServletInboundRequest(host, port));

                return new ListenHandle() {

                    @Override
                    public ListenCookie getCookie() {
                        return new ServletCookie(port);
                    }

                    @Override
                    public void close() {
                        // do nothing
                    }
                };
            }

            @Override
            public void checkPermissions() {
                // do nothing
            }
        });
        return new ServletBasedEndpoint(host, port);
    }

    private static class ServletCookie implements ListenCookie {

        private final int port;

        ServletCookie(int port) {
            this.port = port;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + port;
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
            ServletCookie other = (ServletCookie) obj;
            if (port != other.port)
                return false;
            return true;
        }

    }

}
