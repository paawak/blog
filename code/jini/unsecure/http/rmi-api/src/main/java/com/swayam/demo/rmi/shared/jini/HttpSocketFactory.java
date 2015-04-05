package com.swayam.demo.rmi.shared.jini;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSocketFactory extends SocketFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSocketFactory.class);

    // private final SocketFactory delegatingSocketFactory;

    public HttpSocketFactory() {
        // delegatingSocketFactory = SocketFactory.getDefault();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        LOGGER.debug("1111111111111111111111111111111111111111111");
        return SocketFactory.getDefault().createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        LOGGER.debug("2222222222222222222222222222222222222222222222222");
        return SocketFactory.getDefault().createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        LOGGER.debug("3333333333333333333333333333333333333333333333333333");
        return SocketFactory.getDefault().createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        LOGGER.debug("44444444444444444444444444444444444444444444444444444");
        return SocketFactory.getDefault().createSocket(address, port, localAddress, localPort);
    }

    @Override
    public Socket createSocket() throws UnknownHostException, IOException {
        LOGGER.debug("555555555555555555555555555555");
        // return new Socket("localhost", 8100);
        return new Socket();
    }

}
