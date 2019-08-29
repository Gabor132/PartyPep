package com.gabor.integration.controllers;

import com.gabor.common.IntegrationTestConfiguration;
import com.gabor.integration.controllers.interfaces.PostRequestTestInterface;
import com.gabor.partypeps.enums.RequestPathEnum;
import com.gabor.partypeps.models.dto.MessageDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

@IntegrationTestConfiguration(path = RequestPathEnum.ADD_MESSAGE)
public class AddMessageRequestTestIT extends AbstractRequestTest implements PostRequestTestInterface {

    @Override
    public MessageDTO getDTO() {
        MessageDTO message = new MessageDTO();
        message.sourceUserId = 1L;
        message.text = "Salut";
        message.groupId = 1L;
        return message;
    }

    @Test
    public void testAddGroupRequest() {
        HttpResponse response = this.doPostRequest(getDTO());
        this.testPostStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }


    @Test
    public void testAddGroupRequestAuthenticated() {
        HttpResponse response = this.doPostRequest(getDTO(), true);
        this.testPostStatusCode(HttpStatus.SC_CREATED, response);
        this.testPostMessageType(response);
        this.testPostResponsePayload(response);
    }
}
