package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.MessageDTO;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.util.List;

@IntegrationTestConfiguration(path = RequestPathEnum.GET_ALL_MESSAGES)
public class GetAllMessagesRequestTestIT extends AbstractGetRequestTest<MessageDTO> {

    @Test
    public void testGetAllMessagesRequest() {
        HttpResponse response = this.doGetRequest();
        this.testGetStatusCode(response);
        this.testGetMessageType(response);
        this.testGetResponsePayload(MessageDTO.class, List.class, response);
    }
}
