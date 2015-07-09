package com.swayam.demo.countdownlatch;

import java.util.concurrent.CountDownLatch;

import javax.swing.JProgressBar;

public class ProgressUpdater implements Runnable {

    private final CountDownLatch start;
    private final CountDownLatch end;
    private final JProgressBar bar;
    private final int totalSteps;
    private final JProgressBar overallBar;
    private final int overallValue;

    public ProgressUpdater(CountDownLatch start, CountDownLatch end, JProgressBar bar, int totalStepsForBar, JProgressBar overallBar, int overallValue) {
	this.start = start;
	this.end = end;
	this.bar = bar;
	this.totalSteps = totalStepsForBar;
	this.overallBar = overallBar;
	this.overallValue = overallValue;
    }

    @Override
    public void run() {
	try {
	    start.await();
	    int minValue = bar.getMinimum();
	    int maxValue = bar.getMaximum();
	    for (int i = 1; i <= totalSteps; i++) {
		int currentValue = i * (maxValue - minValue) / totalSteps;
		bar.setValue(currentValue);
		Thread.sleep(1000);
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} finally {
	    overallBar.setValue(overallValue);
	    end.countDown();
	}
    }
}
