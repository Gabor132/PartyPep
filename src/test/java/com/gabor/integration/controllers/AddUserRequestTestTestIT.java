package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.integration.controllers.interfaces.PostRequestTestInterface;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.UserDTO;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.util.ArrayList;

@IntegrationTestConfiguration(path = RequestPathEnum.ADD_USER)
public class AddUserRequestTestTestIT extends AbstractRequestTest implements PostRequestTestInterface {

    @Override
    public UserDTO getDTO() {
        UserDTO user = new UserDTO();
        user.groupIds = new ArrayList<>();
        user.invitationIds = new ArrayList<>();
        user.name = "User de Test";
        user.password = "ceva parola acolo";
        return user;
    }

    @Test
    public void testAddUserRequest() {
        HttpResponse response = this.doPostRequest(getDTO());
        this.testPostStatusCode(response);
        this.testPostMessageType(response);
        this.testPostResponsePayload(response);
    }
}
