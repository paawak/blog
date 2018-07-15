package com.swayam.demo.trx.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {

	@GetMapping(path = { "/book.html" })
	public ModelAndView showAddAuthorGenrePage() {
		return new ModelAndView("book");
	}

	@GetMapping(path = { "/rating.html" })
	public ModelAndView showAddPersonPage() {
		return new ModelAndView("rating");
	}

}
