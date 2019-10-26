package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_CLIENT_ID)
public class GetClientIdRequestIT extends AbstractRequestTest {


    @Test
    public void testGetClientId() {
        HttpResponse response = this.doGetRequest();
        this.testResponseStatusCode(HttpStatus.SC_OK, response);
    }

    @Test
    public void testGetClientIdAuthorized() {
        HttpResponse response = this.doGetRequest(true);
        this.testResponseStatusCode(response);
        this.testResponseMessageType(response);
    }
}
