package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.MessageDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.List;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_PRIVATE_MESSAGES, hasBoolean = true)
public class GetPrivateUnreadMessagesRequestTestIT extends AbstractGetRequestTest<MessageDTO> {

    @Test
    public void testGetAllUnreadMessagesRequest() {
        HttpResponse response = this.doGetRequest();
        this.testResponseStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }

    @Test
    public void testGetAllUnreadMessagesRequestAuthenticated() {
        HttpResponse response = this.doGetRequest(true);
        this.testResponseStatusCode(response);
        this.testResponseMessageType(response);
        this.testGetResponsePayload(MessageDTO.class, List.class, response);
    }
}
