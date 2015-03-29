package com.swayam.demo.rmi.api.shared;

import java.io.IOException;

import net.jini.jeri.OutboundRequest;

import com.sun.jini.jeri.internal.http.HttpClientSocketFactory;

public class JettyClientConnection extends HttpClientConnectionWithHook {

    public JettyClientConnection(String host, int port, HttpClientSocketFactory factory, HttpClientManager manager) throws IOException {
        super(host, port, factory, manager);
    }

    public JettyClientConnection(String targetHost, int targetPort, String proxyHost, int proxyPort, boolean tunnel, boolean persist, HttpClientSocketFactory factory,
            HttpClientManager manager) throws IOException {
        super(targetHost, targetPort, proxyHost, proxyPort, tunnel, persist, factory, manager);
    }

    @Override
    protected OutboundRequest getOutboundRequestInstance() throws IOException {
        // return new HttpOutboundRequest("localhost", 8100);
        return super.getOutboundRequestInstance();
    }

    @Override
    public OutboundRequest newRequest() throws IOException {
        return super.newRequest();
    }

}
