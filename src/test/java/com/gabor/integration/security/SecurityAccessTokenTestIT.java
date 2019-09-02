package com.gabor.integration.security;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.integration.controllers.AbstractRequestTest;
import com.gabor.partypeps.enums.RequestPathEnum;
import org.apache.http.HttpResponse;
import org.junit.Test;


@IntegrationTestConfiguration(path = RequestPathEnum.GET_ACCESS_TOKEN)
public class SecurityAccessTokenTestIT extends AbstractRequestTest {

    @Test
    public void testAccessToken(){
        HttpResponse response = doAccessTokenPostRequest();
        //
        // Test status code
        testResponseStatusCode(response);
        //
        // Test message type
        testResponseMessageType(response);
        //
        // Retrieve the Response as a Security Token
        SecurityToken securityToken = extractAccessToken(response);
        testSecurityToken(securityToken);
        //
        //
        logger.info("Received during test, token: " + securityToken);
    }

    @Test
    public void testRefreshAccessToken(){
        //
        // First get the normal Access Token
        HttpResponse response = doAccessTokenPostRequest();
        SecurityToken securityToken = extractAccessToken(response);
        //
        // Do the call to get a new Access Token using the refresh token
        response = doRefreshTokenPostRequest(securityToken.refresh_token);
        //
        // Test Status Code
        testResponseStatusCode(response);
        //
        // Test message type
        testResponseMessageType(response);
        //
        // Retrieve the response as a SecurityToken
        securityToken = extractAccessToken(response);
        testSecurityToken(securityToken);
        logger.info("Received during test, refresh token: " + securityToken);
    }
}
