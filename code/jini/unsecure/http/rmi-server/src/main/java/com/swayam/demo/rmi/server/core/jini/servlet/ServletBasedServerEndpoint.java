package com.swayam.demo.rmi.server.core.jini.servlet;

import java.io.IOException;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.Endpoint;
import net.jini.jeri.RequestDispatcher;
import net.jini.jeri.ServerEndpoint;

import com.swayam.demo.rmi.shared.jini.servlet.ServletBasedEndpoint;

public class ServletBasedServerEndpoint implements ServerEndpoint {

    private final String serverUrl;

    public ServletBasedServerEndpoint(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        return InvocationConstraints.EMPTY;
    }

    @Override
    public Endpoint enumerateListenEndpoints(ListenContext listenContext) throws IOException {
        listenContext.addListenEndpoint(new ListenEndpoint() {

            @Override
            public ListenHandle listen(RequestDispatcher requestDispatcher) throws IOException {

                // requestDispatcher.dispatch(new
                // ServletInboundRequest(serverUrl));

                return new ListenHandle() {

                    @Override
                    public ListenCookie getCookie() {
                        return new ServletCookie(serverUrl);
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
        return new ServletBasedEndpoint(serverUrl);
    }

    private static class ServletCookie implements ListenCookie {

        private final String serverUrl;

        ServletCookie(String serverUrl) {
            this.serverUrl = serverUrl;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((serverUrl == null) ? 0 : serverUrl.hashCode());
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
            if (serverUrl == null) {
                if (other.serverUrl != null)
                    return false;
            } else if (!serverUrl.equals(other.serverUrl))
                return false;
            return true;
        }

    }

}
