package com.gabor.integration.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabor.integration.auxiliar.JSONRetriver;
import com.gabor.partypeps.models.dto.AbstractDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.junit.Assert;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGetRequestTest<T extends AbstractDTO> extends AbstractRequestTest {


    protected Object getGetResponsePayload(Class className, HttpEntity response) {
        return JSONRetriver.retrieveClass(className, response);
    }

    protected void testGetResponsePayload(Class mainClass, HttpResponse response) {
        this.testGetResponsePayload(mainClass, null, response);
    }

    protected void testGetResponsePayload(Class mainClass, Class wrapperClass, HttpResponse response) {
        List<Object> finalListOfObjects = new ArrayList<>();
        if (wrapperClass != null) {
            Object responseObject = this.getGetResponsePayload(wrapperClass, response.getEntity());
            if (responseObject instanceof java.util.List) {
                List<Object> listOfObjects = (List<Object>) responseObject;
                for (Object object : listOfObjects) {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        StringEntity entity = new StringEntity(mapper.writeValueAsString(object));
                        Object exactElementObject = this.getGetResponsePayload(mainClass, entity);
                        finalListOfObjects.add(exactElementObject);
                    } catch (JsonProcessingException | UnsupportedEncodingException ex) {
                        Assert.fail("Fail in parsing the exact object class desired from JSON");
                    }
                }
            } else {
                Assert.fail("Failed to parse response to " + List.class.getName());
            }
        } else {
            Object responseObject = this.getGetResponsePayload(mainClass, response);
            finalListOfObjects.add(responseObject);
        }
        if (finalListOfObjects.size() > 0) {
            for (Object object : finalListOfObjects) {
                testDTOConsistency(object);
            }
        }
    }

    protected void testDTOConsistency(Object object) {
        try {
            T dto = (T) object;
            logger.info(dto.toString());
            if (dto != null) {
                Assert.assertTrue("DTO id is not consistent", dto.id != 0);
            } else {
                Assert.fail(String.format("Response is not of desired DTO type but received type %s", object.getClass().getName()));
            }
        } catch (ClassCastException ex) {
            Assert.fail(String.format("Expected the desired DTO type but received type %s", object.getClass().getName()));
        }
    }
}
