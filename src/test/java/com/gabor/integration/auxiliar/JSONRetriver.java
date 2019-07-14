package com.gabor.integration.auxiliar;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class JSONRetriver {

    public static Logger logger =Logger.getLogger(JSONRetriver.class.getName());

    public static Object retrieveClass(Class className, HttpResponse response){
        Object object = null;
        try {
            String json = EntityUtils.toString(response.getEntity());
            logger.info("Response JSON is: " + json);
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            object = mapper.readValue(json, className);
            logger.info("JSON is translated to :" + object.toString());
        }catch(IOException ex){
            logger.severe("Failed to parse JSON into desired object: " + className);
        }
        return object;
    }

}
