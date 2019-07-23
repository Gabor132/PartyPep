package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import org.apache.http.HttpResponse;
import org.junit.Test;

@IntegrationTestConfiguration(path = RequestPathEnum.REMOVE_GROUP_BY_ID, hasId = true, id = 1)
public class RemoveGroupByIdRequestTestIT extends AbstractRequestTest {

    @Test
    public void testDeleteGroupById() {
        HttpResponse response = this.doDeleteRequest();
        this.testDeleteStatusCode(response);
        this.testDeleteMessageType(response);
        this.testDeleteResponsePayload(response);
    }
}
