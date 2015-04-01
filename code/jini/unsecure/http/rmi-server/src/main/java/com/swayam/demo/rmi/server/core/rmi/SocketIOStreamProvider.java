package com.swayam.demo.rmi.server.core.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.swayam.demo.rmi.api.shared.IOStreamProvider;

public class SocketIOStreamProvider implements IOStreamProvider {

    private final Socket socket;

    public SocketIOStreamProvider(Socket socket) {
        this.socket = socket;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

}
