package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.UserDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.List;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_ALL_USERS)
public class GetAllUsersRequestTestIT extends AbstractGetRequestTest<UserDTO> {

    @Test
    public void testGetAllUsersRequest() {
        HttpResponse response = this.doGetRequest();
        this.testResponseStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }

    @Test
    public void testGetAllUsersRequestAuthenticated() {
        HttpResponse response = this.doGetRequest(true);
        this.testResponseStatusCode(response);
        this.testResponseMessageType(response);
        this.testGetResponsePayload(UserDTO.class, List.class, response);
    }
}
