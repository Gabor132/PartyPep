package com.gabor.integration.controllers;

import com.gabor.common.AbstractTest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;

public abstract class AbstractControllerRequestTest extends AbstractTest {

    public abstract void testStatusCode();

    public abstract void testMessageType();

    protected final void defaultTestStatusCode(){
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        try {
            // When
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

            // Then
            Assert.assertEquals("Checking status 202", httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        }catch(ClientProtocolException ex){
            Assert.fail("Client Protocol Exception thrown " + ex.toString());
        }catch (IOException ex){
            Assert.fail("IO Exception thrown " + ex.toString());
        }
    }

    protected final void defaultTestMessageType(){
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        try {
            // When
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

            // Then
            String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
            Assert.assertEquals("Content type is not application/json", MimeTypeUtils.APPLICATION_JSON_VALUE, mimeType );
        }catch(ClientProtocolException ex){
            Assert.fail("Client Protocol Exception thrown " + ex.toString());
        }catch (IOException ex){
            Assert.fail("IO Exception thrown " + ex.toString());
        }
    }

    public abstract void testPayload();

    protected abstract String getUrl();


}
