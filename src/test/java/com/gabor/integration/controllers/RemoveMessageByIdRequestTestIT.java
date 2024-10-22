package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

@IntegrationTestConfiguration(path = RequestPathEnum.REMOVE_MESSAGE_BY_ID, hasId = true, id = 1)
public class RemoveMessageByIdRequestTestIT extends AbstractRequestTest {

    @Test
    public void testDeleteMessageById() {
        HttpResponse response = this.doDeleteRequest();
        this.testResponseStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }

    @Test
    public void testDeleteMessageByIdAuthenticated() {
        HttpResponse response = this.doDeleteRequest(true);
        this.testResponseStatusCode(response);
        this.testResponseMessageType(response);
        this.testDeleteResponsePayload(response);
    }
}
