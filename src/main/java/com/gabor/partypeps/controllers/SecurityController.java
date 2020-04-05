package com.gabor.partypeps.controllers;

import com.gabor.partypeps.common.env.EnvironmentHelper;
import com.gabor.partypeps.common.props.PropertiesHelper;
import com.gabor.partypeps.enums.PropertiesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController(value = "app-security")
@RequestMapping(path = "app-security")
public class SecurityController {

    @Autowired
    public EnvironmentHelper environmentHelper;

    @GetMapping(path = "/clientId")
    @ResponseBody
    public Map<String, String> getClientId(){
        HashMap<String, String> client = new HashMap<>();
        Properties properties = PropertiesHelper.getSecurityProperties(true, environmentHelper.getEnvironment());
        client.put("client_id", PropertiesHelper.getProperty(properties, PropertiesEnum.SECURITY_CLIENT_ID));
        client.put("client_secret", PropertiesHelper.getProperty(properties, PropertiesEnum.SECURITY_CLIENT_SECRET));
        return client;
    }
}
