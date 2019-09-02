package com.gabor.integration.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabor.common.AbstractTest;
import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.integration.auxiliar.JSONRetriver;
import com.gabor.integration.controllers.enums.PropertiesEnum;
import com.gabor.integration.security.SecurityToken;
import com.gabor.partypeps.common.PropertiesHelper;
import com.gabor.partypeps.enums.ProfilesEnum;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.AbstractDTO;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class AbstractRequestTest extends AbstractTest {

    protected static Logger logger = Logger.getLogger(AbstractRequestTest.class.getName());

    /////////////////////////// SECURITY CREDENTIALS RETRIEVAL /////////////////////////////////

    protected static String getCredential(PropertiesEnum key){
        return getCredential(key, "");
    }

    protected static String getCredential(PropertiesEnum key, String defaultValue){
        Properties properties = PropertiesHelper.getSecurityProperties(true, ProfilesEnum.IT);
        boolean isFromEnvironment = Boolean.parseBoolean(properties.getProperty(PropertiesEnum.FROM_ENV_KEY.getValue()));
        String returnValue = isFromEnvironment ? System.getenv(properties.getProperty(key.getValue())) : properties.getProperty(key.getValue());
        if(returnValue == null || returnValue.isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    /////////////////////////// OAUTH2 TOKEN HANDLING //////////////////////////////////////////

    private String getTokenUrl(){
        String ACCESS_TOKEN_URL = RequestPathEnum.GET_ACCESS_TOKEN.getUrl();
        return getUrl(ACCESS_TOKEN_URL);
    }

    protected final SecurityToken extractAccessToken(HttpResponse response){
        Object responseObject = JSONRetriver.retrieveClass(SecurityToken.class, response.getEntity());
        return (SecurityToken) responseObject;
    }

    protected final HttpResponse doAccessTokenPostRequest(){
        // Given
        HttpPost request = new HttpPost(getTokenUrl());
        HttpResponse httpResponse = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            //
            // Set the Basic Credentials for the Token Request
            attachBasicCredentialsToRequest(clientBuilder, request);
            //
            // Map JSON for Token Credentials
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "password"));
            params.add(new BasicNameValuePair("client_id", "partypeps"));
            params.add(new BasicNameValuePair("username", getCredential(PropertiesEnum.USERNAME_KEY, "admin")));
            params.add(new BasicNameValuePair("password", getCredential(PropertiesEnum.PASSWORD_KEY, "admin")));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
            request.setEntity(entity);
            // To Request
            httpResponse = clientBuilder.build().execute(request);

        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return httpResponse;
    }

    protected final HttpResponse doRefreshTokenPostRequest(String refresh_token){
        // Given
        HttpPost request = new HttpPost(getTokenUrl());
        HttpResponse httpResponse = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            //
            // Set the Basic Credentials for the Token Request
            attachBasicCredentialsToRequest(clientBuilder, request);
            //
            // Map JSON for Token Credentials
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "refresh_token"));
            params.add(new BasicNameValuePair("refresh_token", refresh_token));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
            request.setEntity(entity);

            // To Request
            httpResponse = clientBuilder.build().execute(request);

        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return httpResponse;
    }

    private String getAnyAccessToken(){
        HttpResponse response = doAccessTokenPostRequest();
        Assert.assertNotNull(response);
        SecurityToken token = extractAccessToken(response);
        logger.info("Got token: " + token.access_token);
        return token.access_token;
    }

    private String getAnyRefreshToken(){
        HttpResponse response = doAccessTokenPostRequest();
        Assert.assertNotNull(response);
        return extractAccessToken(response).refresh_token;
    }

    /////////////////////////// REQUEST METHODS ////////////////////////////////////////////////

    private void attachBasicCredentialsToRequest(HttpClientBuilder clientBuilder, HttpRequest request){
        try {
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials
                    = new UsernamePasswordCredentials("partypeps", "dGakldj192038");
            provider.setCredentials(AuthScope.ANY, credentials);
            request.addHeader(new BasicScheme().authenticate(credentials, request, null));
            clientBuilder.setDefaultCredentialsProvider(provider);
        } catch (AuthenticationException ex){
            Assert.fail("Failed to attach Authentication segment to request" + ex.getMessage());
        }
    }

    private void attachOAuthCredentialsToRequest(HttpRequest request, String accessToken){
        if(accessToken == null || accessToken.isEmpty()) {
            request.setHeader("Authorization", "Bearer " + accessToken);
        }
    }

    protected final HttpResponse doGetRequest(){
        return doGetRequest(false);
    }

    protected final HttpResponse doGetRequest(boolean withCredentials){
        String accessToken = null;
        if(withCredentials){
            SecurityToken token = extractAccessToken(doAccessTokenPostRequest());
            accessToken = token.access_token;
        }
        return doGetRequest(withCredentials, accessToken);
    }

    protected final HttpResponse doGetRequest(boolean withCredentials, String accessToken) {
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        HttpResponse httpResponse = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            if(withCredentials){
                attachOAuthCredentialsToRequest(request, accessToken);
            }
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

    final HttpResponse doDeleteRequest(boolean withCredentials){
        return doDeleteRequest(withCredentials, getAnyAccessToken());
    }

    final HttpResponse doDeleteRequest(boolean withCredentials, String accessToken) {
        // Given
        HttpUriRequest request = new HttpDelete(getUrl());
        HttpResponse httpResponse = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            if(withCredentials){
                attachOAuthCredentialsToRequest(request, accessToken);
            }
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

    final HttpResponse doPostRequest(AbstractDTO dto, boolean withCredentials){
        return doPostRequest(dto, withCredentials, getAnyAccessToken());
    }

    final HttpResponse doPostRequest(AbstractDTO dto, boolean withCredentials, String accessToken) {
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
                request.addHeader("Accept", MimeTypeUtils.APPLICATION_JSON_VALUE);
                request.addHeader("Content-type", MimeTypeUtils.APPLICATION_JSON_VALUE);
                try {
                    HttpClientBuilder clientBuilder = HttpClientBuilder.create();
                    if(withCredentials){
                        attachOAuthCredentialsToRequest(request, accessToken);
                    }
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

    protected final void testResponseStatusCode(HttpResponse response){
        testResponseStatusCode(HttpStatus.SC_OK, response);
    }

    protected final void testResponseStatusCode(int desiredStatusCode, HttpResponse response){
        Assert.assertEquals("Checking status failed", desiredStatusCode, response.getStatusLine().getStatusCode());
    }

    /////////////////////////// MESSAGE TYPE ////////////////////////////////////////////////

    protected final void testResponseMessageType(HttpResponse response){
        testResponseMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE, response);
    }

    protected final void testResponseMessageType(String desiredType, HttpResponse response){
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

    /////////////////////////// TOKEN //////////////////////////////////////////////////

    protected void testSecurityToken(SecurityToken token){
        Assert.assertNotNull("Token is null",token);
        //
        // Check the Token
        Assert.assertNotNull( "Not a valid token, access_token is empty", token.access_token);
        Assert.assertNotNull( "Not a valid token, refresh_token is empty", token.refresh_token);
        Assert.assertNotNull( "Not a valid token, expires_in is empty", token.expires_in);
        Assert.assertNotNull( "Not a valid token, token_type is empty", token.token_type);
        Assert.assertNotNull( "Not a valid token, scope is empty", token.scope);
        Assert.assertNotNull( "Not a valid token, jti is empty", token.jti);
    }

    /////////////////////////// GET URL ////////////////////////////////////////////////

    private String getUrl(){
        return getUrl(null);
    }

    private String getUrl(String url) {
        String finalURL = PropertiesHelper.getURLProperties(true, ProfilesEnum.IT).getProperty("url");
        if(url == null || url.isEmpty()) {
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
