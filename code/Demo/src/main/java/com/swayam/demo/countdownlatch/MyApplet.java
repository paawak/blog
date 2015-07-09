package com.swayam.demo.countdownlatch;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MyApplet extends JApplet {

    @Override
    public void init() {
	try {
	    SwingUtilities.invokeAndWait(new Runnable() {

		@Override
		public void run() {
		    getContentPane().add(new ProgressPanel(), BorderLayout.CENTER);
		}
	    });
	} catch (InvocationTargetException | InterruptedException e) {
	    e.printStackTrace();
	}
    }

}
