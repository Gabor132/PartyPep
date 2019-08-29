package com.gabor.partypeps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping(value = "/error", path = "/error")
    public String getError() {
        return "Sorry";
    }

}
