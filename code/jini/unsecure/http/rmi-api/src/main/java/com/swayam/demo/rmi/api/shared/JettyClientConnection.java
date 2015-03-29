package com.swayam.demo.rmi.api.shared;

import java.io.IOException;

import net.jini.jeri.OutboundRequest;

import com.sun.jini.jeri.internal.http.HttpClientConnection;
import com.sun.jini.jeri.internal.http.HttpClientManager;
import com.sun.jini.jeri.internal.http.HttpClientSocketFactory;

public class JettyClientConnection extends HttpClientConnection {

    /* states */
    private static final int IDLE = 0;
    private static final int BUSY = 1;
    private static final int CLOSED = 2;

    private final Object stateLock = new Object();
    private int state = IDLE;

    private final HttpClientManager manager;

    public JettyClientConnection(String host, int port, HttpClientSocketFactory factory, HttpClientManager manager) throws IOException {
        super(host, port, factory, manager);
        this.manager = manager;
    }

    public JettyClientConnection(String targetHost, int targetPort, String proxyHost, int proxyPort, boolean tunnel, boolean persist, HttpClientSocketFactory factory,
            HttpClientManager manager) throws IOException {
        super(targetHost, targetPort, proxyHost, proxyPort, tunnel, persist, factory, manager);
        this.manager = manager;
    }

    /**
     * Initiates new request to connection target. Throws an IOException if the
     * connection is currently busy.
     */
    @Override
    public OutboundRequest newRequest() throws IOException {
        if (false) {
            OutboundRequest req = null;
            markBusy();

            manager.clearServerInfo();

            try {
                req = new HttpOutboundRequest("localhost", 8100);
                return req;
            } finally {
                if (req == null) {
                    markIdle();
                }
            }
        } else {
            return super.newRequest();
        }

    }

    /**
     * Marks connection busy. Throws IOException if connection closed.
     */
    private void markBusy() throws IOException {
        synchronized (stateLock) {
            if (state == BUSY) {
                throw new IOException("connection busy");
            } else if (state == CLOSED) {
                throw new IOException("connection closed");
            }
            state = BUSY;
        }
    }

    /**
     * Marks connection idle. Does nothing if connection closed.
     */
    private void markIdle() {
        synchronized (stateLock) {
            if (state == CLOSED) {
                return;
            }
            state = IDLE;
        }
        idle();
    }

}
