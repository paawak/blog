package com.swayam.demo.rmi.server.core.jini;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.io.context.AcknowledgmentSource;
import net.jini.jeri.InboundRequest;

import com.swayam.demo.rmi.api.shared.Header;
import com.swayam.demo.rmi.api.shared.MessageReader;
import com.swayam.demo.rmi.api.shared.MessageWriter;
import com.swayam.demo.rmi.api.shared.Request;

/**
 * HTTP-based implementation of InboundRequest abstraction.
 */
class InboundRequestImpl extends Request implements InboundRequest {

    private final MessageReader reader;
    private final MessageWriter writer;
    private String cookie;
    private boolean corrupt = false;

    /**
     * Creates new InboundRequestImpl which uses given MessageReader and
     * MessageWriter instances to read/write request content.
     */
    InboundRequestImpl(MessageReader reader, MessageWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void checkPermissions() {
    }

    public InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        return InvocationConstraints.EMPTY;
    }

    public void populateContext(Collection context) {

    }

    public InputStream getRequestInputStream() {
        return getInputStream();
    }

    public OutputStream getResponseOutputStream() {
        return getOutputStream();
    }

    /**
     * Returns true if stream corrupted, false if stream ok.
     */
    boolean streamCorrupt() {
        return corrupt;
    }

    protected void startOutput() throws IOException {
        // start line, header already written
    }

    protected void write(byte[] b, int off, int len) throws IOException {
        writer.writeContent(b, off, len);
    }

    protected void endOutput() throws IOException {
        if (cookie != null) {
            Header trailer = new Header();
            trailer.setField("RMI-Response-Cookie", cookie);
            writer.writeTrailer(trailer);
        } else {
            writer.writeTrailer(null);
        }
    }

    protected boolean startInput() throws IOException {
        return true; // header already read
    }

    protected int read(byte[] b, int off, int len) throws IOException {
        return reader.readContent(b, off, len);
    }

    protected int available() throws IOException {
        return reader.availableContent();
    }

    protected void endInput() throws IOException {

    }

    protected void addAckListener(AcknowledgmentSource.Listener listener) {

    }

    protected void done(boolean corrupt) {
        this.corrupt = corrupt;
    }
}