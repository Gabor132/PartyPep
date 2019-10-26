package com.gabor.integration.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabor.common.AbstractTest;
import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.integration.auxiliar.JSONRetriver;
import com.gabor.partypeps.enums.PropertiesEnum;
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

    private static Properties securityProperties = PropertiesHelper.getSecurityProperties(true, ProfilesEnum.IT);

    /////////////////////////// SECURITY CREDENTIALS RETRIEVAL /////////////////////////////////

    /**
     * Function used to get the Security properties from the security.properties file or from the environment using the file
     * @param key
     * @param defaultValue
     * @return String representing the desired property value
     */
    static String getCredential(PropertiesEnum key, String defaultValue){
        Properties properties = PropertiesHelper.getSecurityProperties(true, ProfilesEnum.IT);
        boolean isFromEnvironment = Boolean.parseBoolean(properties.getProperty(PropertiesEnum.FROM_ENV_KEY.getValue()));
        String returnValue = isFromEnvironment ? System.getenv(properties.getProperty(key.getValue())) : properties.getProperty(key.getValue());
        if(returnValue == null || returnValue.isEmpty()) {
            return defaultValue;
        }
        return returnValue;
    }

    //////////////////////////// BASIC AUTHENTICATION ///////////////////////////////////////////

    /**
     * Function used to attach the BASIC Authentication header to a request. Used only by the Access/Refresh Token requests from now on.
     * @param clientBuilder
     * @param request
     */
    private void attachBasicCredentialsToRequest(HttpClientBuilder clientBuilder, HttpRequest request){
        try {
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials
                    = new UsernamePasswordCredentials(
                    PropertiesHelper.getProperty(securityProperties, PropertiesEnum.SECURITY_CLIENT_ID),
            PropertiesHelper.getProperty(securityProperties, PropertiesEnum.SECURITY_CLIENT_SECRET));
            provider.setCredentials(AuthScope.ANY, credentials);
            request.addHeader(new BasicScheme().authenticate(credentials, request, null));
            clientBuilder.setDefaultCredentialsProvider(provider);
        } catch (AuthenticationException ex){
            Assert.fail("Failed to attach Authentication segment to request" + ex.getMessage());
        }
    }

    /////////////////////////// OAUTH2 TOKEN HANDLING //////////////////////////////////////////

    /**
     * Given a JSON, will return a SecurityToken object containing the JWT structure
     * @param response
     * @return SecurityToken - JWT structure
     */
    protected final SecurityToken extractAccessToken(HttpResponse response){
        Object responseObject = JSONRetriver.retrieveClass(SecurityToken.class, response.getEntity());
        return (SecurityToken) responseObject;
    }

    /**
     * Function used to do the generic Access Token POST Request
     * @return HttpResponse
     */
    protected final HttpResponse doAccessTokenPostRequest(){
        // Given
        HttpPost request = new HttpPost(getUrl(RequestPathEnum.GET_ACCESS_TOKEN.getUrl()));
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
            params.add(new BasicNameValuePair("client_id",  PropertiesHelper.getProperty(securityProperties, PropertiesEnum.SECURITY_CLIENT_ID)));
            params.add(new BasicNameValuePair("username",  PropertiesHelper.getProperty(securityProperties, PropertiesEnum.SECURITY_TEST_USERNAME)));
            params.add(new BasicNameValuePair("password",  PropertiesHelper.getProperty(securityProperties, PropertiesEnum.SECURITY_TEST_PASSWORD)));
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

    /**
     * Function used to do the generic Refresh Token POST Request
     * @param refresh_token
     * @return HttpResponse
     */
    protected final HttpResponse doRefreshTokenPostRequest(String refresh_token){
        // Given
        HttpPost request = new HttpPost(getUrl(RequestPathEnum.GET_ACCESS_TOKEN.getUrl()));
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

    /**
     * Quick function that returns the access_token value from a JSON Web Token (JWT),
     * this function also does the request and conversion to the SecurityToken structure
     * @return String (Access Token) to be used in further requests for Authorization
     */
    private String getAccessToken(){
        HttpResponse response = doAccessTokenPostRequest();
        Assert.assertNotNull(response);
        SecurityToken token = extractAccessToken(response);
        logger.info("Got token: " + token.access_token);
        return token.access_token;
    }

    /////////////////////////// REQUEST METHODS ////////////////////////////////////////////////

    /**
     * Function used to do a GET Request directed at the tested API
     * @return HttpResponse
     */
    final HttpResponse doGetRequest(){
        return doGetRequest(false);
    }

    /**
     * Function used to do a GET Request directed at the tested API
     * @param withCredentials - default false
     * @return HttpResponse
     */
    final HttpResponse doGetRequest(boolean withCredentials){
        return doGetRequest(withCredentials, withCredentials ? getAccessToken() : null);
    }

    /**
     * Function used to do a GET Request directed at the tested API
     * @param withCredentials - default is false
     * @param accessToken - default is null
     * @return HttpResponse
     */
    final HttpResponse doGetRequest(boolean withCredentials, String accessToken) {
        // Given
        HttpUriRequest request = new HttpGet(getUrl());
        HttpResponse httpResponse = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            if(withCredentials){
                request.addHeader("Authorization", "Bearer " + accessToken);
            }
            httpResponse = clientBuilder.build().execute(request);
        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return httpResponse;
    }

    /**
     * Function used to do a DELETE Request directed at the tested API
     * @return HttpResponse
     */
    final HttpResponse doDeleteRequest(){
        return doDeleteRequest(false);
    }

    /**
     * Function used to do a DELETE Request directed at the tested API
     * @param withCredentials - default false
     * @return HttpResponse
     */
    final HttpResponse doDeleteRequest(boolean withCredentials){
        return doDeleteRequest(withCredentials, withCredentials ? getAccessToken() : null);
    }

    /**
     * Function used to do a DELETE Request directed at the tested API
     * @param withCredentials - default false
     * @param accessToken - default null
     * @return HttpResponse
     */
    final HttpResponse doDeleteRequest(boolean withCredentials, String accessToken) {
        // Given
        HttpUriRequest request = new HttpDelete(getUrl());
        HttpResponse httpResponse = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            if(withCredentials){
                request.addHeader("Authorization", "Bearer " + accessToken);
            }
            httpResponse = clientBuilder.build().execute(request);

        } catch (ClientProtocolException ex) {
            Assert.fail("Client Protocol Exception thrown " + ex.getMessage());
        } catch (IOException ex) {
            Assert.fail("IO Exception thrown " + ex.getMessage());
        }
        return httpResponse;
    }

    /**
     * Function used to do a POST Request directed at the tested API
     * @param dto - Object to be transformed to JSON and sent
     * @return HttpResponse
     */
    final HttpResponse doPostRequest(AbstractDTO dto){
        return doPostRequest(dto, false);
    }

    /**
     * Function used to do a POST Request directed at the tested API
     * @param dto - Object to be transformed to JSON and sent
     * @param withCredentials - default false
     * @return HttpResponse
     */
    final HttpResponse doPostRequest(AbstractDTO dto, boolean withCredentials){
        return doPostRequest(dto, withCredentials, withCredentials ? getAccessToken() : null);
    }

    /**
     * Function used to do a POST Request directed at the tested API
     * @param dto - Object to be transformed to JSON and sent
     * @param withCredentials - default false
     * @param accessToken - default null
     * @return HttpResponse
     */
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
                        request.addHeader("Authorization", "Bearer " + accessToken);
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

    /////////////////////////// STATUS CODE TESTING ////////////////////////////////////////////////

    protected final void testResponseStatusCode(HttpResponse response){
        testResponseStatusCode(HttpStatus.SC_OK, response);
    }

    protected final void testResponseStatusCode(int desiredStatusCode, HttpResponse response){
        Assert.assertEquals("Checking status failed", desiredStatusCode, response.getStatusLine().getStatusCode());
    }

    /////////////////////////// MESSAGE TYPE TESTING ////////////////////////////////////////////////

    protected final void testResponseMessageType(HttpResponse response){
        testResponseMessageType(MimeTypeUtils.APPLICATION_JSON_VALUE, response);
    }

    protected final void testResponseMessageType(String desiredType, HttpResponse response){
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assert.assertEquals("Content type is not of desired type", desiredType, mimeType);
    }

    /////////////////////////// PAYLOAD TESTING ////////////////////////////////////////////////

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

    /////////////////////////// TOKEN TESTING //////////////////////////////////////////////////

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


    /**
     * Function used to get the proper URL to use upon a tests HTTP Requests
     * (either it is given, or it is taken from the @IntegrationTestConfiguration annotation)
     * @return String - the URL to be used for the Requests
     */
    private String getUrl(){
        return getUrl(null);
    }

    /**
     * Function used to get the proper URL to use upon a tests HTTP Requests
     * (either it is given, or it is taken from the @IntegrationTestConfiguration annotation)
     * @param url - default null
     * @return String - the URL to be used for the Requests
     */
    private String getUrl(String url) {
        String finalURL = PropertiesHelper.getURLProperties(true, ProfilesEnum.IT).getProperty(PropertiesEnum.URL_URL.getValue());
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
