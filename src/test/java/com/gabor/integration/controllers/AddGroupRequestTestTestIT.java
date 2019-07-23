package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.integration.controllers.interfaces.PostRequestTestInterface;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.AbstractDTO;
import com.gabor.partypeps.models.dto.GroupDTO;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.util.ArrayList;

@IntegrationTestConfiguration(path = RequestPathEnum.ADD_GROUP)
public class AddGroupRequestTestTestIT extends AbstractRequestTest implements PostRequestTestInterface {

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
        this.testPostStatusCode(response);
        this.testPostMessageType(response);
        this.testPostResponsePayload(response);
    }
}
