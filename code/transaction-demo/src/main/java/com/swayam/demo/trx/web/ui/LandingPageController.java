package com.swayam.demo.trx.web.ui;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LandingPageController {

	@GetMapping(path = { "/", "/index.jsp", "/welcome.jsp", "/index.html", "welcome.html" })
	public ModelAndView handleLandingPage() {
		Map<String, Object> miscMap = new HashMap<>();
		miscMap.put("Date", LocalDateTime.now());
		miscMap.put("User Home", System.getProperty("user.home"));
		return new ModelAndView("welcome");
	}

}
