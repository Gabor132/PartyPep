package com.gabor.partypeps.controllers;

import com.gabor.partypeps.common.props.PropertiesHelper;
import com.gabor.partypeps.enums.ProfilesEnum;
import com.gabor.partypeps.models.dao.AbstractEntity;
import com.gabor.partypeps.services.AbstractService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This is the basic controller to get the index page of the application, note: it is @Controller, the @RestController will always
 * return a basic response and not the HTML page
 *
 * @author dragos.gabor
 */
@RestController
public class IndexController extends AbstractController<AbstractEntity> {

    @GetMapping(value = "/", path = "/")
    public String index() {
        return "index";
    }

    @Override
    public AbstractService getService() {
        return null;
    }

    private Map<String, String> propertiesToMap(Properties properties) {
        HashMap<String, String> map = new HashMap<>();
        for (Object s : properties.keySet()) {
            String key = (String) s;
            map.put(key, properties.getProperty(key));
        }
        return map;
    }

    @GetMapping(value = "/check/security/{profile}")
    @ResponseBody
    public Map<String, String> checkSecurity(@PathVariable String profile) {
        return propertiesToMap(PropertiesHelper.getSecurityProperties(true, ProfilesEnum.valueOf(profile)));
    }

    @GetMapping(value = "/check/jdbc/{profile}")
    @ResponseBody
    public Map<String, String> checkJDBC(@PathVariable String profile) {
        return propertiesToMap(PropertiesHelper.getJDBCProperties(true, ProfilesEnum.valueOf(profile)));
    }
}
