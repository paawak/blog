package com.swayam.demo.web.rest.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LandingPageController {

    @RequestMapping(path = { "/", "/index.jsp", "/welcome.jsp", "/index.html", "welcome.html" }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView handleLandingPage() {
	Map<String, Object> miscMap = new HashMap<>();
	miscMap.put("Date", LocalDateTime.now());
	miscMap.put("User Home", System.getProperty("user.home"));
	Map<String, Object> userObjects = new HashMap<>();
	userObjects.put("user", System.getProperty("user.name"));
	userObjects.put("miscMap", miscMap);
	return new ModelAndView("welcome", userObjects);
    }

}
