package com.swayam.demo.miglayout;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MigLayoutDemo1 extends JFrame {

    public MigLayoutDemo1() {
	init();
    }

    private void init() {
	getContentPane().setLayout(new MigLayout("fill"));
	getContentPane().add(getMainPanel());
	setSize(500, 600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel getMainPanel() {
	JPanel mainPanel = new JPanel();
	mainPanel.setLayout(new MigLayout("fill", "[fill,100][fill, grow, 150:150:][fill][fill, grow][fill]", "[][]"));
	// ------------------- row 1
	// col 1
	mainPanel.add(new JLabel("Name"));
	// col 2
	mainPanel.add(new JTextField());
	// col 3
	mainPanel.add(new JLabel("Employee ID"));
	// col 4
	mainPanel.add(new JTextField(), "w 200");
	// col 5
	mainPanel.add(new JTextField(), "w 70!, wrap");
	// ------------------- row 2
	// sub-row 1, col 1
	mainPanel.add(new JLabel("Other Details"));
	// sub-row 1, col 2
	JPanel redPanel = new JPanel();
	redPanel.setBackground(Color.RED);
	mainPanel.add(redPanel, "w 200!, h 200!, spanx 2, spany 2");
	// sub-row 1, col 3
	mainPanel.add(new JTextField(), "spanx 2, wrap");
	// sub-row 2, col 1
	mainPanel.add(new JLabel("Some Label"));
	// sub-row 2, col 2
	mainPanel.add(new JTextField(), "spanx 3, wrap");
	// --------------------------- row 3
	// sub-row 1, col 1
	JPanel bluePanel = new JPanel();
	bluePanel.setBackground(Color.BLUE);
	mainPanel.add(bluePanel, "w 100!, h 300!, spanx 1, spany 3, wrap");
	// sub-row 2, col 2
	mainPanel.add(new JTextField(), "spanx 5,wrap");
	// sub-row 3, col 1
	mainPanel.add(new JTextField(), "spanx 5, wrap");

	return mainPanel;
    }

    public static void main(String[] a) {
	try {
	    SwingUtilities.invokeAndWait(new Runnable() {

		@Override
		public void run() {
		    JFrame frame = new MigLayoutDemo1();
		    frame.setVisible(true);
		}

	    });
	} catch (InvocationTargetException | InterruptedException e) {
	    e.printStackTrace();
	}
    }

}
