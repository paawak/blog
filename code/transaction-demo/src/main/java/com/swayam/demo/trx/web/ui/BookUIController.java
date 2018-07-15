package com.swayam.demo.trx.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookUIController {

	@GetMapping(path = { "/book.html" })
	public ModelAndView showAddPersonPage() {
		return new ModelAndView("book");
	}

}
