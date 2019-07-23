package com.gabor.integration.controllers;


import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.UserDTO;
import org.apache.http.HttpResponse;
import org.junit.Test;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_USER_BY_ID, hasId = true, id = 1)
public class GetUserByIdRequestTestIT extends AbstractGetRequestTest {

    @Test
    public void testGetUserById() {
        HttpResponse response = this.doGetRequest();
        this.testGetStatusCode(response);
        this.testGetMessageType(response);
        this.testGetResponsePayload(UserDTO.class, response);
    }

}
