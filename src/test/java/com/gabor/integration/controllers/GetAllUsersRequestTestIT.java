package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.UserDTO;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_ALL_USERS)
public class GetAllUsersRequestTestIT extends AbstractGetRequestTest<UserDTO> {

    public static Logger logger = Logger.getLogger(GetAllUsersRequestTestIT.class.getName());

    @Test
    public void testGetAllUsersRequest() {
        HttpResponse response = this.doGetRequest();
        this.testGetStatusCode(response);
        this.testGetMessageType(response);
        this.testGetResponsePayload(UserDTO.class, List.class, response);
    }
}
