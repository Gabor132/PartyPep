package com.gabor.integration.auxiliar;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class JSONRetriver {

    public static Logger logger = Logger.getLogger(JSONRetriver.class.getName());

    public static Object retrieveClass(Class desiredClass, HttpEntity response) {
        Object object = null;
        try {
            String json = EntityUtils.toString(response);
            logger.info("Response JSON is: " + json);
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, false);
            object = mapper.readValue(json, desiredClass);
            logger.info("JSON is translated to :" + object.toString());
        } catch (IOException ex) {
            logger.severe("Failed to parse JSON into desired object: " + desiredClass);
        }
        return object;
    }

}
