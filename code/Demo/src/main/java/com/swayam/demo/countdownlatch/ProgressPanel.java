package com.swayam.demo.countdownlatch;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class ProgressPanel extends JPanel {

    private CountDownLatch startTrigger;
    private CountDownLatch endTrigger;

    private JProgressBar bar1;
    private JSpinner spinner1;

    private JProgressBar bar2;
    private JSpinner spinner2;

    private JProgressBar bar3;
    private JSpinner spinner3;

    private JProgressBar bar4;
    private JSpinner spinner4;

    private JProgressBar overallBar;

    private JButton startButton;

    public ProgressPanel() {
	initPanel();
    }

    private void initPanel() {
	setLayout(new GridBagLayout());
	GridBagConstraints gridBagConstraints;

	// 1st row
	JLabel progressBarLabel = new JLabel("Progress Bars");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.gridwidth = 2;
	add(progressBarLabel, gridBagConstraints);

	JLabel runTimeLabel = new JLabel("Run For Steps");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 0;
	add(runTimeLabel, gridBagConstraints);

	// 2nd row
	bar1 = new JProgressBar(0, 100);
	spinner1 = createJSpinner();
	createProgressBarRow(bar1, spinner1, "One: ", 1);

	// 3rd row
	bar2 = new JProgressBar(0, 100);
	spinner2 = createJSpinner();
	createProgressBarRow(bar2, spinner2, "Two: ", 2);

	// 4th row
	bar3 = new JProgressBar(0, 100);
	spinner3 = createJSpinner();
	createProgressBarRow(bar3, spinner3, "Three: ", 3);

	// 5th row
	bar4 = new JProgressBar(0, 100);
	spinner4 = createJSpinner();
	createProgressBarRow(bar4, spinner4, "Four: ", 4);

	// 6th row
	JLabel overallBarLabel = new JLabel("Overall Progress: ");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 5;
	add(overallBarLabel, gridBagConstraints);

	overallBar = new JProgressBar(0, 100);
	overallBar.setStringPainted(true);
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.anchor = GridBagConstraints.WEST;
	gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
	gridBagConstraints.insets = new Insets(0, 0, 0, 20);
	add(overallBar, gridBagConstraints);

	// 7th row
	startButton = new JButton("Start");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.gridwidth = 3;
	gridBagConstraints.insets = new Insets(0, 20, 0, 20);
	add(startButton, gridBagConstraints);

	startButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		enableWidgets(false);

		bar1.setValue(0);
		bar2.setValue(0);
		bar3.setValue(0);
		bar4.setValue(0);
		overallBar.setValue(0);

		startTrigger = new CountDownLatch(1);
		endTrigger = new CountDownLatch(1);
		CountDownLatch latch1 = new CountDownLatch(1);
		CountDownLatch latch2 = new CountDownLatch(1);
		CountDownLatch latch3 = new CountDownLatch(1);

		new Thread(new ProgressUpdater(startTrigger, latch1, bar1, (int) spinner1.getValue(), overallBar, 25)).start();

		new Thread(new ProgressUpdater(latch1, latch2, bar2, (int) spinner2.getValue(), overallBar, 50)).start();

		new Thread(new ProgressUpdater(latch2, latch3, bar3, (int) spinner1.getValue(), overallBar, 75)).start();

		new Thread(new ProgressUpdater(latch3, endTrigger, bar4, (int) spinner1.getValue(), overallBar, 100)).start();

		new Thread(new Runnable() {
		    @Override
		    public void run() {
			try {
			    endTrigger.await();
			} catch (InterruptedException e) {
			    e.printStackTrace();
			} finally {
			    enableWidgets(true);
			}
		    }
		}).start();

		startTrigger.countDown();
	    }
	});
    }

    private void createProgressBarRow(JProgressBar bar, JSpinner spinner, String progressBarText, int gridy) {
	GridBagConstraints gridBagConstraints;

	JLabel barLabel = new JLabel(progressBarText);
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = gridy;
	gridBagConstraints.anchor = GridBagConstraints.WEST;
	add(barLabel, gridBagConstraints);

	bar.setStringPainted(true);
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = gridy;
	gridBagConstraints.anchor = GridBagConstraints.CENTER;
	gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
	add(bar, gridBagConstraints);

	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = gridy;
	add(spinner, gridBagConstraints);
    }

    private JSpinner createJSpinner() {
	JSpinner spinner = new JSpinner(new SpinnerNumberModel(10, 5, 200, 5));
	return spinner;
    }

    private void enableWidgets(boolean enable) {
	spinner1.setEnabled(enable);
	spinner2.setEnabled(enable);
	spinner3.setEnabled(enable);
	spinner4.setEnabled(enable);
	startButton.setEnabled(enable);
    }

}
