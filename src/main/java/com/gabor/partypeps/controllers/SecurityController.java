package com.gabor.partypeps.controllers;

import com.gabor.partypeps.common.EnvironmentHelper;
import com.gabor.partypeps.common.PropertiesHelper;
import com.gabor.partypeps.enums.PropertiesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This is the controller meant to return to the calling consuming application it's
 * Client ID for OAuth Authentication
 * (basic data)
 *
 * @author dragos.gabor
 */
@RestController(value = "app-security")
@RequestMapping(path = "app-security")
public class SecurityController {

    @Autowired
    private EnvironmentHelper environment;

    @GetMapping(path = "/clientId")
    @ResponseBody
    public Map<String,String> getAPIKey(HttpServletRequest request){
        Properties properties = PropertiesHelper.getSecurityProperties(true, environment.getEnvironment());
        HashMap<String, String> map = new HashMap<>();
        map.put("client_id", PropertiesHelper.getProperty(properties, PropertiesEnum.SECURITY_CLIENT_ID));
        map.put("client_secret", PropertiesHelper.getProperty(properties, PropertiesEnum.SECURITY_CLIENT_SECRET));
        return map;
    }
}
