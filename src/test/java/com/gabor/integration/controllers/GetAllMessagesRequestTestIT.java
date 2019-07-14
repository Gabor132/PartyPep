package com.gabor.integration.controllers;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dto.MessageDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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
public class GetAllMessagesRequestTestIT extends AbstractControllerRequestTest {

    @Autowired
    public String mainURL;

    @Test
    @Override
    public void testStatusCode() {
        this.defaultGetTestStatusCode();
    }

    @Test
    @Override
    public void testMessageType() {
        this.defaultGetTestMessageType();
    }

    @Test
    @Override
    public void testPayload() {
        Object object = this.defaultGetTestPayload(ArrayList.class);
        if(object instanceof java.util.List) {
            List<MessageDTO> messages = (List<MessageDTO>) object;
            if(messages.size() > 0){
                for(MessageDTO message : messages){
                    Assert.assertTrue(message.id != 0);
                }
            }
        }else{
            Assert.fail("Failed to parse response to " + List.class.getName());
        }
    }

    @Override
    protected String getUrl() {
        return mainURL + "messages/all";
    }
}
