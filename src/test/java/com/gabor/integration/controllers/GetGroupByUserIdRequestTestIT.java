package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.GroupDTO;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.util.List;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_GROUPS_BY_USER_ID, hasId = true, id = 2)
public class GetGroupByUserIdRequestTestIT extends AbstractGetRequestTest<GroupDTO> {

    @Test
    public void testGetGroupsByUserId() {
        HttpResponse response = this.doGetRequest();
        this.testGetStatusCode(response);
        this.testGetMessageType(response);
        this.testGetResponsePayload(GroupDTO.class, List.class, response);
    }

}
