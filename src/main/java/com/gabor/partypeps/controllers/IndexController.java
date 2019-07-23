package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.AbstractEntity;
import com.gabor.partypeps.services.AbstractService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This is the basic controller to get the index page of the application, note: it is @Controller, the @RestController will always
 * return a basic response and not the HTML page
 *
 * @author dragos.gabor
 */
@Controller
public class IndexController extends AbstractController<AbstractEntity> {

    @GetMapping(value = "/", path = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/favicon.ico")
    public String favicon() {
        return "";
    }

    @Override
    public AbstractService getService() {
        return null;
    }
}
