package com.swayam.demo.combo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class LargeTextComboFrameWithCustomRenderer1 extends JFrame {

    private static final List<String> LARGE_COMBO_BOX_ITEMS = Arrays
	    .asList("JNLP is supposed to be the latest and greatest in the Java Applet world post 1.6 release! Personally, I was never a great fan of Applets. I have always felt that Applets are a pain in the wrong place. Inherently difficult and cumbersome to use and deploy. But some of these concerns have been addressed",
		    "This is really tiny",
		    "As shown above, the 4 progress bars start one after another after the start button is clicked. The following code is for the JPanel with the 4 progress bars: @SuppressWarnings(\"serial\") public class ProgressPanel extends JPanel {   private CountDownLatch startTrigger; private CountDownLatch endTrigger;   private JProgressBar bar1; private JSpinner spinner1;   private JProgressBar bar2; private");

    public LargeTextComboFrameWithCustomRenderer1() {
	init();
    }

    private void init() {
	getContentPane().setLayout(new BorderLayout());
	JLabel title = new JLabel("Large Text Combo Demo: Custom ComboBox Renderer 1, With Max and Preferred Size Hints", JLabel.CENTER);
	getContentPane().add(title, BorderLayout.NORTH);
	JPanel mainPanel = new JPanel();
	mainPanel.setLayout(new FlowLayout());
	JLabel comboLabel = new JLabel("Large Combo: ", JLabel.LEFT);
	mainPanel.add(comboLabel);
	mainPanel.add(getLargeComboBox());
	getContentPane().add(mainPanel, BorderLayout.CENTER);
	setSize(new Dimension(600, 500));
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JComboBox<String> getLargeComboBox() {
	JComboBox<String> largeComboBox = new JComboBox<>();
	for (String item : LARGE_COMBO_BOX_ITEMS) {
	    largeComboBox.addItem(item);
	}
	largeComboBox.setRenderer(new LargeComboBoxRenderer1(200));
	largeComboBox.setPreferredSize(new Dimension(200, 30));
	largeComboBox.setMaximumSize(new Dimension(200, 30));
	return largeComboBox;
    }

    public static void main(String[] args) {
	try {
	    SwingUtilities.invokeAndWait(new Runnable() {

		@Override
		public void run() {
		    LargeTextComboFrameWithCustomRenderer1 frame = new LargeTextComboFrameWithCustomRenderer1();
		    frame.setVisible(true);
		}

	    });
	} catch (InvocationTargetException | InterruptedException e) {
	    e.printStackTrace();
	}
    }

}
