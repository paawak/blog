package com.swayam.demo.rmi.shared.jini;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOStreamProvider {

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

}
