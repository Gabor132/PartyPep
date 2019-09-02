package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.integration.controllers.interfaces.PostRequestTestInterface;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.AbstractDTO;
import com.gabor.partypeps.models.dto.GroupDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.ArrayList;

@IntegrationTestConfiguration(path = RequestPathEnum.ADD_GROUP)
public class AddGroupRequestTestIT extends AbstractRequestTest implements PostRequestTestInterface {

    @Override
    public AbstractDTO getDTO() {
        GroupDTO group = new GroupDTO();
        group.name = "Banana";
        group.userIds = new ArrayList<>();
        group.userIds.add(1L);
        group.userIds.add(2L);
        return group;
    }

    @Test
    public void testAddGroupRequest() {
        HttpResponse response = this.doPostRequest(getDTO());
        this.testResponseStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }

    @Test
    public void testAddGroupRequestAuthenticated() {
        HttpResponse response = this.doPostRequest(getDTO(), true);
        this.testResponseStatusCode(HttpStatus.SC_CREATED, response);
        this.testResponseMessageType(response);
        this.testPostResponsePayload(response);
    }
}
