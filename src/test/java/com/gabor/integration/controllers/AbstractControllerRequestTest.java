package com.gabor.integration.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabor.common.AbstractTest;
import com.gabor.integration.auxiliar.JSONRetriver;
import com.gabor.partypeps.models.dto.AbstractDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractControllerRequestTest extends AbstractTest {


    /////////////////////////// STATUS CODE ////////////////////////////////////////////////

    public abstract void testStatusCode();

    protected final void testGetStatusCode(){
        this.testGetStatusCode(HttpStatus.SC_OK);
    }

    protected final void testGetStatusCode(int desiredStatusCode){
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        try {
            // When
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

            // Then
            Assert.assertEquals("Checking status failed", desiredStatusCode, httpResponse.getStatusLine().getStatusCode());
        }catch(ClientProtocolException ex){
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        }catch (IOException ex){
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
    }

    protected final void testDeleteStatusCode(){
        this.testDeleteStatusCode(HttpStatus.SC_OK);
    }

    protected final void testDeleteStatusCode(int desiredStatusCode){
        // Given
        HttpUriRequest request = new HttpDelete(getUrl());
        try {
            // When
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

            // Then
            Assert.assertEquals("Checking status failed", desiredStatusCode, httpResponse.getStatusLine().getStatusCode());
        }catch(ClientProtocolException ex){
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        }catch (IOException ex){
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
    }

    protected final void testPostStatusCode(AbstractDTO dto){
        this.testPostStatusCode(HttpStatus.SC_OK, dto);
    }

    protected final void testPostStatusCode(int desiredStatusCode, AbstractDTO dto){
        // Given
        HttpPost request = new HttpPost(getUrl());
        //
        // Set payload
        //
        ObjectMapper mapper = new ObjectMapper();
        try {
            try {
                HttpEntity entity = new StringEntity(mapper.writeValueAsString(dto));
                request.setEntity(entity);
                request.setHeader("Accept", MimeTypeUtils.APPLICATION_JSON_VALUE);
                request.setHeader("Content-type", MimeTypeUtils.APPLICATION_JSON_VALUE);
                try {
                    // When
                    HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
                    // Then
                    Assert.assertEquals("Checking status failed", desiredStatusCode, httpResponse.getStatusLine().getStatusCode());
                } catch (ClientProtocolException ex) {
                    Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
                } catch (IOException ex) {
                    Assert.fail("IO Exception thrown " + ex.getMessage());
                }
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
    }

    /////////////////////////// MESSAGE TYPE ////////////////////////////////////////////////

    public abstract void testMessageType();

    protected final void testGetMessageType(){
        this.testGetMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE);
    }

    protected final void testGetMessageType(String desiredType){
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        try {
            // When
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

            // Then
            String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
            Assert.assertEquals("Content type is not of desired type", desiredType, mimeType );
        }catch(ClientProtocolException ex){
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        }catch (IOException ex){
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
    }

    protected final void testDeleteMessageType(){
        this.testDeleteMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE);
    }

    protected final void testDeleteMessageType(String desiredType){
        // Given
        HttpUriRequest request = new HttpDelete(getUrl());
        try {
            // When
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

            // Then
            String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
            Assert.assertEquals("Content type is not of desired type", desiredType, mimeType );
        }catch(ClientProtocolException ex){
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        }catch (IOException ex){
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
    }

    protected final void testPostMessageType(AbstractDTO dto){
        this.testPostMessageType(MimeTypeUtils.TEXT_HTML_VALUE, dto);
    }

    protected final void testPostMessageType(String desiredType, AbstractDTO dto){
        // Given
        HttpPost request = new HttpPost(getUrl());
        //
        // Set payload
        //
        List<NameValuePair> params = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            params.add(new BasicNameValuePair("messageDto", mapper.writeValueAsString(dto)));

            try {
                HttpEntity entity = new StringEntity(mapper.writeValueAsString(dto));
                request.setHeader("Accept", MimeTypeUtils.APPLICATION_JSON_VALUE);
                request.setHeader("Content-Type", MimeTypeUtils.APPLICATION_JSON_VALUE);
                try {
                    // When
                    HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
                    // Then
                    String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
                    Assert.assertEquals("Content type is not of desired type", desiredType, mimeType);
                } catch (ClientProtocolException ex) {
                    ex.printStackTrace();
                    Assert.fail("Client Protocol Exception thrown " + ex.getLocalizedMessage());
                } catch (IOException ex) {
                    Assert.fail("IO Exception thrown " + ex.getLocalizedMessage());
                }
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
    }

    /////////////////////////// PAYLOAD ////////////////////////////////////////////////

    public abstract void testPayload();

    protected Object testGetPayload(Class className){
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        Object object = null;
        try {
            // When
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            // Then
            object = JSONRetriver.retrieveClass(className, httpResponse);
        }catch(ClientProtocolException ex){
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        }catch (IOException ex){
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return object;
    }

    protected void testDeletePayload(){
        // Given
        HttpDelete request = new HttpDelete(getUrl());
        //
        // Set payload
        //
        List<NameValuePair> params = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // When
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            // Then
            Boolean isSuccessful = (Boolean) JSONRetriver.retrieveClass(Boolean.class, httpResponse);
            Assert.assertTrue("Deletion of entity was not successful", isSuccessful);
        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
    }

    protected void testPostPayload(AbstractDTO dto){
        // Given
        HttpPost request = new HttpPost(getUrl());
        //
        // Set payload
        //
        ObjectMapper mapper = new ObjectMapper();
        try {
            try {
                HttpEntity entity = new StringEntity(mapper.writeValueAsString(dto));
                request.setEntity(entity);
                request.setHeader("Accept", MimeTypeUtils.APPLICATION_JSON_VALUE);
                request.setHeader("Content-Type", MimeTypeUtils.APPLICATION_JSON_VALUE);
                try {
                    // When
                    HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
                    // Then
                    Object response = JSONRetriver.retrieveClass(Long.class, httpResponse);
                    if(response instanceof Long){
                        Long responseId = (Long) response;
                        Assert.assertTrue("Not a valid ID", responseId >= 0);
                    }else{
                        Assert.fail("Response is not of type Long");
                    }
                } catch (ClientProtocolException ex) {
                    Assert.fail("Client Protocol Exception thrown " + ex.getLocalizedMessage());
                } catch (IOException ex) {
                    Assert.fail("IO Exception thrown " + ex.getLocalizedMessage());
                }
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
    }

    /////////////////////////// GET URL ////////////////////////////////////////////////

    protected abstract String getUrl();


}
