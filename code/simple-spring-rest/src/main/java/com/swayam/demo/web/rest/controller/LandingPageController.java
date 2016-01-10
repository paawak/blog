package com.swayam.demo.web.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandingPageController {

    @RequestMapping(path = { "/", "/index.html", "index.htm" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String handleLandingPage() {
	return "This demoes a simple Rest web app based on Spring. It uses annotation driven Spring configuration. Also, it takes advantage of Servlet 3.x to get rid"
		+ " of web.xml and have a pure-java web application initialiser";
    }

}
