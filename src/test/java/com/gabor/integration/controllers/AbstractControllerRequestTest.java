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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
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

    protected final void defaultGetTestStatusCode(){
        this.defaultGetTestStatusCode(HttpStatus.SC_OK);
    }

    protected final void defaultGetTestStatusCode(int desiredStatusCode){
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

    protected final void defaultPostTestStatusCode(AbstractDTO dto){
        this.defaultPostTestStatusCode(HttpStatus.SC_OK, dto);
    }

    protected final void defaultPostTestStatusCode(int desiredStatusCode, AbstractDTO dto){
        // Given
        HttpPost request = new HttpPost(getUrl());
        //
        // Set payload
        //
        List<NameValuePair> params = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            params.add(new BasicNameValuePair("body", mapper.writeValueAsString(dto)));
            try {
                HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
                request.setEntity(entity);
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

    protected final void defaultGetTestMessageType(){
        this.defaultGetTestMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE);
    }

    protected final void defaultGetTestMessageType(String desiredType){
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

    protected final void defaultPostTestMessageType(AbstractDTO dto){
        this.defaultPostTestMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE, dto);
    }

    protected final void defaultPostTestMessageType(String desiredType, AbstractDTO dto){
        // Given
        HttpPost request = new HttpPost(getUrl());
        //
        // Set payload
        //
        List<NameValuePair> params = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            params.add(new BasicNameValuePair("body", mapper.writeValueAsString(dto)));
            try {
                HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
                request.setEntity(entity);
                try {
                    // When
                    HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
                    // Then
                    String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
                    Assert.assertEquals("Content type is not of desired type", desiredType, mimeType);
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

    /////////////////////////// PAYLOAD ////////////////////////////////////////////////

    public abstract void testPayload();

    protected Object defaultGetTestPayload(Class className){
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

    protected boolean defaultPostTestPayload(Class className, AbstractDTO dto){
        boolean isSuccessful = false;
        // Given
        HttpPost request = new HttpPost(getUrl());
        //
        // Set payload
        //
        List<NameValuePair> params = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            params.add(new BasicNameValuePair("body", mapper.writeValueAsString(dto)));
            try {
                HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
                request.setEntity(entity);
                try {
                    // When
                    HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
                    // Then
                    isSuccessful = (boolean) JSONRetriver.retrieveClass(boolean.class, httpResponse);
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

        return isSuccessful;
    }

    /////////////////////////// GET URL ////////////////////////////////////////////////

    protected abstract String getUrl();


}
