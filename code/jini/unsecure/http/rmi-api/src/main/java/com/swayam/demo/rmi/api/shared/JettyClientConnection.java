package com.swayam.demo.rmi.api.shared;

import java.io.IOException;

import net.jini.jeri.OutboundRequest;

import com.sun.jini.jeri.internal.http.HttpClientSocketFactory;

public class JettyClientConnection extends HttpClientConnectionWithHook {

    public JettyClientConnection(String host, int port, HttpClientSocketFactory factory, HttpClientManager manager) throws IOException {
        super(host, port, factory, manager);
    }

    @Override
    public OutboundRequest newRequest() throws IOException {
        // return new HttpOutboundRequest("localhost", 8100);
        return super.newRequest();
    }

}
