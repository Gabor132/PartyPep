package com.gabor.integration.controllers;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO - Create a Test that will run only for the CI to check actual service calls
 */
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
public class RemoveMessageByIdRequestTestIT extends AbstractControllerRequestTest {

    @Autowired
    public String mainURL;

    @Test
    @Override
    public void testStatusCode() {
        this.testDeleteStatusCode();
    }

    @Test
    @Override
    public void testMessageType() {
        this.testDeleteMessageType();
    }

    @Test
    @Override
    public void testPayload() { this.testDeletePayload(); }

    @Override
    protected String getUrl() {
        return mainURL + "messages/remove/1";
    }
}
