package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.integration.controllers.interfaces.PostRequestTestInterface;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.UserDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.ArrayList;

@IntegrationTestConfiguration(path = RequestPathEnum.ADD_USER)
public class AddUserRequestTestIT extends AbstractRequestTest implements PostRequestTestInterface {

    @Override
    public UserDTO getDTO() {
        UserDTO user = new UserDTO();
        user.groupIds = new ArrayList<>();
        user.subscriptions = new ArrayList<>();
        user.name = "User de Test";
        user.email = "Email de test";
        user.password = "ceva parola acolo";
        user.authorities = new ArrayList<>();
        user.following = new ArrayList<>();
        user.followers = new ArrayList<>();
        return user;
    }

    @Test
    public void testAddUserRequest() {
        HttpResponse response = this.doPostRequest(getDTO());
        this.testResponseStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }

    @Test
    public void testAddUserRequestAuthenticated() {
        HttpResponse response = this.doPostRequest(getDTO(), true);
        this.testResponseStatusCode(HttpStatus.SC_CREATED, response);
        this.testResponseMessageType(response);
        this.testPostResponsePayload(response);
    }
}
