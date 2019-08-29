package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

@IntegrationTestConfiguration(path = RequestPathEnum.REMOVE_GROUP_BY_ID, hasId = true, id = 1)
public class RemoveGroupByIdRequestTestIT extends AbstractRequestTest {

    @Test
    public void testDeleteGroupById() {
        HttpResponse response = this.doDeleteRequest();
        this.testDeleteStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }

    @Test
    public void testDeleteGroupByIdAuthenticated() {
        HttpResponse response = this.doDeleteRequest(true);
        this.testDeleteStatusCode(response);
        this.testDeleteMessageType(response);
        this.testDeleteResponsePayload(response);
    }
}
