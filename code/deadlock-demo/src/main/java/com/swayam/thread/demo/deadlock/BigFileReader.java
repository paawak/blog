package com.swayam.thread.demo.deadlock;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class BigFileReader {

    private final FileReaderCallback fileReaderCallback;

    public BigFileReader(FileReaderCallback fileReaderCallback) {
        this.fileReaderCallback = fileReaderCallback;
    }

    public void readFile(Path file) throws IOException {
        Charset charset = Charset.forName("utf-8");
        try (SeekableByteChannel sbc = Files.newByteChannel(file, StandardOpenOption.READ)) {
            ByteBuffer buf = ByteBuffer.allocate(10);

            while (true) {
                int bytesRead = sbc.read(buf);
                if (bytesRead == -1) {
                    break;
                }

                buf.limit(bytesRead);
                buf.rewind();

                CharBuffer text = charset.decode(buf);

                fileReaderCallback.charactersRead(new String(text.array()));
                buf.flip();
            }
        }
    }

}
