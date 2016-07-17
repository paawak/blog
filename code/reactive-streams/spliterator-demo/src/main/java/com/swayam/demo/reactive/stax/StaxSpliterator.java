package com.swayam.demo.reactive.stax;

import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class StaxSpliterator<T> implements Spliterator<T>, EndOfDocumentListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaxSpliterator.class);

    private final BlockingQueue<T> buffer;
    private boolean endOfDocument;

    private static final long TIMEOUT = 100;;

    StaxSpliterator(BlockingQueue<T> buffer) {
	this.buffer = buffer;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {

	if (endOfDocument) {
	    return false;
	}

	T nonNullElement = null;

	LOGGER.trace("the buffer is empty, waiting for some time...");

	try {
	    nonNullElement = buffer.poll(TIMEOUT, TimeUnit.MILLISECONDS);
	} catch (InterruptedException e) {
	    throw new RuntimeException(e);
	}

	if (nonNullElement == null) {
	    LOGGER.trace("terminating as received null after waiting");
	    return false;
	}

	action.accept(nonNullElement);

	return true;
    }

    @Override
    public Spliterator<T> trySplit() {
	return null;
    }

    @Override
    public long estimateSize() {
	return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
	return Spliterator.NONNULL | Spliterator.CONCURRENT;
    }

    @Override
    public void endOfDocument() {
	LOGGER.info("end of document event received");
	endOfDocument = true;
    }

}