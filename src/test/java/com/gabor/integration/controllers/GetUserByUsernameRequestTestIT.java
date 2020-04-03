package com.gabor.integration.controllers;


import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.UserDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_USER_BY_NAME, hasName = true, name = "admin")
public class GetUserByUsernameRequestTestIT extends AbstractGetRequestTest {

    @Test
    public void testGetUserByUsername() {
        HttpResponse response = this.doGetRequest();
        testResponseStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }

    @Test
    public void testGetUserByUsernameAuthenticated(){
        HttpResponse response = doGetRequest(true);
        testResponseStatusCode(response);
        testResponseMessageType(response);
        testGetResponsePayload(UserDTO.class, response);
    }


}
