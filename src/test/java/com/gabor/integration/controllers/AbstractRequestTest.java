package com.gabor.integration.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabor.common.AbstractTest;
import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.integration.auxiliar.JSONRetriver;
import com.gabor.partypeps.common.PropertiesHelper;
import com.gabor.partypeps.enums.ProfilesEnum;
import com.gabor.partypeps.models.dto.AbstractDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public abstract class AbstractRequestTest extends AbstractTest {

    public static Logger logger = Logger.getLogger(AbstractRequestTest.class.getName());

    /////////////////////////// REQUEST METHODS ////////////////////////////////////////////////

    final protected HttpResponse doGetRequest() {
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        HttpResponse httpResponse = null;
        try {
            // When
            httpResponse = HttpClientBuilder.create().build().execute(request);
        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return httpResponse;
    }

    final protected HttpResponse doDeleteRequest() {
        // Given
        HttpUriRequest request = new HttpDelete(getUrl());
        HttpResponse httpResponse = null;
        try {
            // When
            httpResponse = HttpClientBuilder.create().build().execute(request);

        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return httpResponse;
    }

    final protected HttpResponse doPostRequest(AbstractDTO dto) {
        // Given
        HttpPost request = new HttpPost(getUrl());
        //
        // Set payload
        //
        ObjectMapper mapper = new ObjectMapper();
        HttpResponse httpResponse = null;
        try {
            try {
                HttpEntity entity = new StringEntity(mapper.writeValueAsString(dto));
                request.setEntity(entity);
                request.setHeader("Accept", MimeTypeUtils.APPLICATION_JSON_VALUE);
                request.setHeader("Content-type", MimeTypeUtils.APPLICATION_JSON_VALUE);
                try {
                    // When
                    httpResponse = HttpClientBuilder.create().build().execute(request);
                } catch (ClientProtocolException ex) {
                    Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
                } catch (IOException ex) {
                    Assert.fail("IO Exception thrown " + ex.getMessage());
                }
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return httpResponse;
    }

    /////////////////////////// STATUS CODE ////////////////////////////////////////////////

    protected final void testGetStatusCode(HttpResponse response) {
        this.testGetStatusCode(HttpStatus.SC_OK, response);
    }

    protected final void testGetStatusCode(int desiredStatusCode, HttpResponse response) {
        Assert.assertEquals("Checking status failed", desiredStatusCode, response.getStatusLine().getStatusCode());
    }

    protected final void testDeleteStatusCode(HttpResponse response) {
        this.testDeleteStatusCode(HttpStatus.SC_OK, response);
    }

    protected final void testDeleteStatusCode(int desiredStatusCode, HttpResponse response) {
        Assert.assertEquals("Checking status failed", desiredStatusCode, response.getStatusLine().getStatusCode());
    }

    protected final void testPostStatusCode(HttpResponse response) {
        this.testPostStatusCode(HttpStatus.SC_OK, response);
    }

    protected final void testPostStatusCode(int desiredStatusCode, HttpResponse response) {
        Assert.assertEquals("Checking status failed", desiredStatusCode, response.getStatusLine().getStatusCode());
    }

    /////////////////////////// MESSAGE TYPE ////////////////////////////////////////////////

    protected final void testGetMessageType(HttpResponse response) {
        this.testGetMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE, response);
    }

    protected final void testGetMessageType(String desiredType, HttpResponse response) {
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assert.assertEquals("Content type is not of desired type", desiredType, mimeType);
    }

    protected final void testDeleteMessageType(HttpResponse response) {
        this.testDeleteMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE, response);
    }

    protected final void testDeleteMessageType(String desiredType, HttpResponse response) {
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assert.assertEquals("Content type is not of desired type", desiredType, mimeType);
    }

    protected final void testPostMessageType(HttpResponse response) {
        this.testPostMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE, response);
    }

    protected final void testPostMessageType(String desiredType, HttpResponse response) {
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assert.assertEquals("Content type is not of desired type", desiredType, mimeType);
    }

    /////////////////////////// PAYLOAD ////////////////////////////////////////////////

    protected Object getGetResponsePayload(Class className, HttpResponse response) {
        return JSONRetriver.retrieveClass(className, response.getEntity());
    }

    protected void testDeleteResponsePayload(HttpResponse response) {
        Boolean isSuccessful = (Boolean) JSONRetriver.retrieveClass(Boolean.class, response.getEntity());
        Assert.assertTrue("Deletion of entity was not successful", isSuccessful);
    }

    protected Object testPostResponsePayload(HttpResponse response) {
        Object responseObject = JSONRetriver.retrieveClass(Long.class, response.getEntity());
        if (responseObject instanceof Long) {
            Long responseId = (Long) responseObject;
            Assert.assertTrue("Not a valid ID", responseId >= 0);
        } else {
            Assert.fail("Response is not of type Long");
        }
        return responseObject;
    }

    /////////////////////////// GET URL ////////////////////////////////////////////////

    final private String getUrl() {
        IntegrationTestConfiguration itAnnotation = this.getClass().getAnnotation(IntegrationTestConfiguration.class);
        String mainURL = PropertiesHelper.getURLProperties(true, ProfilesEnum.IT).getProperty("url");
        String finalURL = mainURL;
        finalURL = finalURL + itAnnotation.path().getUrl();
        if (itAnnotation.hasId()) {
            long id = itAnnotation.id();
            finalURL = finalURL.replace("{id}", Long.toString(id));
        }
        logger.info("URL USED IS: " + finalURL);
        return finalURL;
    }
}
