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
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.springframework.security.web.header.Header;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

enum PropertiesEnum{
    FROM_ENV_KEY("isFromEnv"),
    USERNAME_KEY("username"),
    PASSWORD_KEY("password");
    String value;
    PropertiesEnum(String value){
        this.value = value;
    }
}

public abstract class AbstractRequestTest extends AbstractTest {

    protected static Logger logger = Logger.getLogger(AbstractRequestTest.class.getName());

    /////////////////////////// SECURITY CREDENTIALS RETRIEVAL /////////////////////////////////

    private String getCredential(PropertiesEnum key){
        return getCredential(key, "");
    }

    private String getCredential(PropertiesEnum key, String defaultValue){
        Properties properties = PropertiesHelper.getSecurityProperties(true, ProfilesEnum.IT);
        boolean isFromEnvironment = Boolean.parseBoolean(properties.getProperty(PropertiesEnum.FROM_ENV_KEY.value));
        String returnValue = isFromEnvironment ? System.getenv(properties.getProperty(key.value)) : properties.getProperty(key.value);
        if(returnValue == null || returnValue.isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    /////////////////////////// OAUTH2 TOKEN HANDLING //////////////////////////////////////////

    private String getAccessTokenUrl(){
        String ACCESS_TOKEN_URL = "/oauth/token";
        return getUrl(ACCESS_TOKEN_URL);
    }

    private String getRefreshTokenUrl(){
        String REFRESH_TOKEN_URL = "/oauth/refresh_token";
        return getUrl(REFRESH_TOKEN_URL);
    }

    /**
     * TODO: Handle the OAUTH2 Tokens
     * @return
     */
    private String getAccessToken(){
        // Given
        HttpUriRequest request = new HttpGet(getAccessTokenUrl());
        HttpResponse httpResponse = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials
                    = new UsernamePasswordCredentials("partypeps", "dGakldj192038");
            provider.setCredentials(AuthScope.ANY, credentials);
            ArrayList<Header> headers = new ArrayList<>();
            headers.add(new Header("grant_type", "password"));
            headers.add(new Header("client_id", "partypeps"));
            headers.add(new Header("username", getCredential(PropertiesEnum.USERNAME_KEY, "admin")));
            headers.add(new Header("password", getCredential(PropertiesEnum.PASSWORD_KEY, "admin")));
            clientBuilder.setDefaultCredentialsProvider(provider);
            // When
            httpResponse = clientBuilder.build().execute(request);

        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return "";
    }

    /**
     * TODO: Handle the OAUTH2 Refresh Tokens
     * @return
     */
    private String getRefreshToken(){
        return "";
    }

    /////////////////////////// REQUEST METHODS ////////////////////////////////////////////////

    final HttpResponse doGetRequest(){
        return doGetRequest(false);
    }

    final HttpResponse doGetRequest(boolean withCredentials) {
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        HttpResponse httpResponse = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            if(withCredentials){
                CredentialsProvider provider = new BasicCredentialsProvider();
                UsernamePasswordCredentials credentials
                        = new UsernamePasswordCredentials(getCredential(PropertiesEnum.USERNAME_KEY, "admin"), getCredential(PropertiesEnum.PASSWORD_KEY, "admin"));
                provider.setCredentials(AuthScope.ANY, credentials);
                clientBuilder.setDefaultCredentialsProvider(provider);
            }
            // When
            httpResponse = clientBuilder.build().execute(request);
        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return httpResponse;
    }

    final HttpResponse doDeleteRequest(){
        return doDeleteRequest(false);
    }

    final HttpResponse doDeleteRequest(boolean withCredentials) {
        // Given
        HttpUriRequest request = new HttpDelete(getUrl());
        HttpResponse httpResponse = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            if(withCredentials){
                CredentialsProvider provider = new BasicCredentialsProvider();
                UsernamePasswordCredentials credentials
                        = new UsernamePasswordCredentials(getCredential(PropertiesEnum.USERNAME_KEY, "admin"), getCredential(PropertiesEnum.PASSWORD_KEY, "admin"));
                provider.setCredentials(AuthScope.ANY, credentials);
                clientBuilder.setDefaultCredentialsProvider(provider);
            }
            // When
            httpResponse = clientBuilder.build().execute(request);

        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return httpResponse;
    }

    final HttpResponse doPostRequest(AbstractDTO dto){
        return doPostRequest(dto, false);
    }

    final HttpResponse doPostRequest(AbstractDTO dto, boolean withCredentials) {
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
                    HttpClientBuilder clientBuilder = HttpClientBuilder.create();
                    if(withCredentials){
                        CredentialsProvider provider = new BasicCredentialsProvider();
                        UsernamePasswordCredentials credentials
                                = new UsernamePasswordCredentials(getCredential(PropertiesEnum.USERNAME_KEY, "admin"), getCredential(PropertiesEnum.PASSWORD_KEY, "admin"));
                        provider.setCredentials(AuthScope.ANY, credentials);
                        clientBuilder.setDefaultCredentialsProvider(provider);
                    }
                    // When
                    httpResponse = clientBuilder.build().execute(request);
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

    final void testGetStatusCode(HttpResponse response) {
        this.testGetStatusCode(HttpStatus.SC_OK, response);
    }

    final void testGetStatusCode(int desiredStatusCode, HttpResponse response) {
        Assert.assertEquals("Checking status failed", desiredStatusCode, response.getStatusLine().getStatusCode());
    }

    final void testDeleteStatusCode(HttpResponse response) {
        this.testDeleteStatusCode(HttpStatus.SC_OK, response);
    }

    final void testDeleteStatusCode(int desiredStatusCode, HttpResponse response) {
        Assert.assertEquals("Checking status failed", desiredStatusCode, response.getStatusLine().getStatusCode());
    }

    final void testPostStatusCode(HttpResponse response) {
        this.testPostStatusCode(HttpStatus.SC_OK, response);
    }

    final void testPostStatusCode(int desiredStatusCode, HttpResponse response) {
        Assert.assertEquals("Checking status failed", desiredStatusCode, response.getStatusLine().getStatusCode());
    }

    /////////////////////////// MESSAGE TYPE ////////////////////////////////////////////////

    final void testGetMessageType(HttpResponse response) {
        this.testGetMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE, response);
    }

    final void testGetMessageType(String desiredType, HttpResponse response) {
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assert.assertEquals("Content type is not of desired type", desiredType, mimeType);
    }

    final void testDeleteMessageType(HttpResponse response) {
        this.testDeleteMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE, response);
    }

    final void testDeleteMessageType(String desiredType, HttpResponse response) {
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assert.assertEquals("Content type is not of desired type", desiredType, mimeType);
    }

    final void testPostMessageType(HttpResponse response) {
        this.testPostMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE, response);
    }

    final void testPostMessageType(String desiredType, HttpResponse response) {
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assert.assertEquals("Content type is not of desired type", desiredType, mimeType);
    }

    /////////////////////////// PAYLOAD ////////////////////////////////////////////////

    Object getGetResponsePayload(Class className, HttpResponse response) {
        return JSONRetriver.retrieveClass(className, response.getEntity());
    }

    void testDeleteResponsePayload(HttpResponse response) {
        Boolean isSuccessful = (Boolean) JSONRetriver.retrieveClass(Boolean.class, response.getEntity());
        Assert.assertTrue("Deletion of entity was not successful", isSuccessful);
    }

    Object testPostResponsePayload(HttpResponse response) {
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

    private String getUrl(){
        return getUrl(null);
    }

    private String getUrl(String url) {
        String finalURL = PropertiesHelper.getURLProperties(true, ProfilesEnum.IT).getProperty("url");
        if(url != null && ! url.isEmpty()) {
            IntegrationTestConfiguration itAnnotation = this.getClass().getAnnotation(IntegrationTestConfiguration.class);
            finalURL = finalURL + itAnnotation.path().getUrl();
            if (itAnnotation.hasId()) {
                long id = itAnnotation.id();
                finalURL = finalURL.replace("{id}", Long.toString(id));
            }
        }else{
            finalURL = finalURL + url;
        }
        logger.info("URL USED IS: " + finalURL);
        return finalURL;
    }
}
