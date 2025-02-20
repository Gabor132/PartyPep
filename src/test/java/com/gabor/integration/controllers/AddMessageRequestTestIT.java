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
        message.sourceUsername = "banana";
        message.text = "Salut";
        message.groupName = "banana";
        return message;
    }

    @Test
    public void testAddGroupRequest() {
        HttpResponse response = this.doPostRequest(getDTO());
        this.testResponseStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
    }


    @Test
    public void testAddGroupRequestAuthenticated() {
        HttpResponse response = this.doPostRequest(getDTO(), true);
        this.testResponseStatusCode(HttpStatus.SC_CREATED, response);
        this.testResponseMessageType(response);
        this.testPostResponsePayload(response);
    }
}
