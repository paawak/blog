package com.swayam.demo.trx.cmt.spring.web.ui;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {

	@GetMapping(path = { "/", "/index.jsp", "/welcome.jsp", "/index.html", "/welcome.html" })
	public ModelAndView handleLandingPage() {
		Map<String, Object> userObjects = new HashMap<>();
		userObjects.put("user", System.getProperty("user.name"));
		return new ModelAndView("welcome", userObjects);
	}

	@GetMapping(path = { "/book.html" })
	public ModelAndView showAddAuthorGenrePage() {
		return new ModelAndView("book");
	}

	@GetMapping(path = { "/rating.html" })
	public ModelAndView showAddPersonPage() {
		return new ModelAndView("rating");
	}

}
