package com.gabor.integration.controllers;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dto.MessageDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = {
        DatabaseConfig.class,
        EntityManagerFactoryConfig.class,
        RepositoryConfiguration.class,
        MapperTestConfiguration.class,
        ServiceTestConfiguration.class,
        UrlTestConfiguration.class
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "IT")
public class AddMessageRequestTestIT extends AbstractControllerRequestTest {

    @Autowired
    public String mainURL;

    @Test
    @Override
    public void testStatusCode() {
        this.testPostStatusCode(getDTO());
    }

    @Test
    @Override
    public void testMessageType() {
        this.testPostMessageType(getDTO());
    }

    @Test
    @Override
    public void testPayload() {
        this.testPostPayload(getDTO());
    }

    private MessageDTO getDTO(){
        MessageDTO message = new MessageDTO();
        message.sourceUserId = 1;
        message.text = "Salut";
        message.groupId = 1;
        return message;
    }

    @Override
    protected String getUrl() {
        return mainURL + "messages/add";
    }
}
