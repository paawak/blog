package com.swayam.demo.countdownlatch;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

@SuppressWarnings("serial")
public class CountDownLatchApplet extends JApplet {

    @Override
    public void init() {
	try {
	    SwingUtilities.invokeAndWait(new Runnable() {

		@Override
		public void run() {
		    getContentPane().setLayout(new BorderLayout());
		    getContentPane().add(new ProgressPanel(), BorderLayout.CENTER);
		}
	    });
	} catch (InvocationTargetException | InterruptedException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void start() {
	showAppletAndHideImage();
    }

    private void showAppletAndHideImage() {
	JSObject window = JSObject.getWindow(this);
	String showAppletDiv = "document.getElementById('applet_div').style.visibility = 'visible';";
	window.eval(showAppletDiv);
	String hideImageDiv = "document.getElementById('image_div').style.visibility = 'hidden';";
	window.eval(hideImageDiv);
    }

}
