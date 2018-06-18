package com.swayam.demo.web.xls.restlet.rest;

import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class JaxRsRestApplication extends JaxRsApplication {

    public JaxRsRestApplication(Context context) {
	super(context);
	add(new RestletApplication());
    }

}
