package com.gabor.party.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is the basic controller to get the index page of the application, note: it is @Controller, the @RestController will always
 * return a basic response and not the HTML page
 * @author dragos.gabor
 *
 */
@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
}
