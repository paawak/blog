package com.swayam.demo.miglayout;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MigLayoutDemo2 {

    public static void main(String[] a) throws Exception {
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	try {
	    SwingUtilities.invokeAndWait(new Runnable() {

		@Override
		public void run() {
		    JDialog frame = new ContactDialog();
		    frame.setVisible(true);
		}

	    });
	} catch (InvocationTargetException | InterruptedException e) {
	    e.printStackTrace();
	}
    }

}
