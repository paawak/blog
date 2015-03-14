package com.swayam.demo.rmi.api.shared;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

public class HttpSocketFactory extends SocketFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    // private final SocketFactory delegatingSocketFactory;

    public HttpSocketFactory() {
        // delegatingSocketFactory = SocketFactory.getDefault();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        System.out.println("1111111111111111111111111111111111111111111");
        return SocketFactory.getDefault().createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        System.out.println("2222222222222222222222222222222222222222222222222");
        return SocketFactory.getDefault().createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        System.out.println("3333333333333333333333333333333333333333333333333333");
        return SocketFactory.getDefault().createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        System.out.println("44444444444444444444444444444444444444444444444444444");
        return SocketFactory.getDefault().createSocket(address, port, localAddress, localPort);
    }

    @Override
    public Socket createSocket() {
        System.out.println("555555555555555555555555555555");
        return new Socket();
    }

}
