package com.swayam.demo.web.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonController {

	@RequestMapping(path = { "person.jsp", "person.html", "person" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView showAddPersonPage() {
		return new ModelAndView("person");
	}

	@RequestMapping(path = { "save/person" }, method = { RequestMethod.POST })
	public ModelAndView savePerson() {
		return new ModelAndView("person");
	}

}
