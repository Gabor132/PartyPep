package com.gabor.party.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is the basic controller to get the index page of the application, note: it is @Controller, the @RestController will always
 * return a basic response and not the HTML page
 * @author dragos.gabor
 *
 */
@Controller(value = "index")
@RequestMapping(path = "/")
public class IndexController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index.html";
	}
	
}
