package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.GroupDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.List;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_ALL_GROUPS)
public class GetAllGroupsRequestTestIT extends AbstractGetRequestTest<GroupDTO> {

    @Test
    public void testGetAllGroups() {
        HttpResponse response = this.doGetRequest();
        this.testResponseStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }


    @Test
    public void testGetAllGroupsAuthenticated() {
        HttpResponse response = this.doGetRequest(true);
        this.testResponseStatusCode(response);
        this.testResponseMessageType(response);
        this.testGetResponsePayload(GroupDTO.class, List.class, response);
    }

}
