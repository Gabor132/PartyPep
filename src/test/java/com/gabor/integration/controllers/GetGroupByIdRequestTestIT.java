package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.GroupDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_GROUP_BY_ID, hasId = true, id = 1)
public class GetGroupByIdRequestTestIT extends AbstractGetRequestTest<GroupDTO> {

    @Test
    public void testGetGroupById() {
        HttpResponse response = this.doGetRequest();
        this.testGetStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }

    @Test
    public void testGetGroupByIdAuthenticated() {
        HttpResponse response = this.doGetRequest(true);
        this.testGetStatusCode(response);
        this.testGetMessageType(response);
        this.testGetResponsePayload(GroupDTO.class, response);
    }

}
