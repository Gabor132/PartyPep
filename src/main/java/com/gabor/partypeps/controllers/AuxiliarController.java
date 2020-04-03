package com.gabor.partypeps.controllers;

import com.gabor.partypeps.common.env.EnvironmentHelper;
import com.gabor.partypeps.common.props.PropertiesHelper;
import com.gabor.partypeps.enums.PropertiesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "auxiliar")
@RequestMapping(path = "auxiliar")
public class AuxiliarController {

    @Autowired
    public EnvironmentHelper environmentHelper;

    @GetMapping(path = "/origins")
    @ResponseBody
    public String allowedOrigins(){
        return PropertiesHelper.getURLProperties(true, environmentHelper.getEnvironment()).getProperty(PropertiesEnum.FRONTEND_URL.getValue());
    }

}
