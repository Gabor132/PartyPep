package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.AbstractEntity;
import com.gabor.partypeps.services.AbstractService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This is the basic controller to get the index page of the application, note: it is @Controller, the @RestController will always
 * return a basic response and not the HTML page
 * @author dragos.gabor
 *
 */
@Controller(value = "index")
@RequestMapping(path = "/")
public class IndexController extends AbstractController<AbstractEntity>{
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index.html";
	}

	@Override
	public AbstractService getService() {
		return null;
	}
}
