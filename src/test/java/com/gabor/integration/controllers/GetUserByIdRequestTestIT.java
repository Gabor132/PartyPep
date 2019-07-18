package com.gabor.integration.controllers;


import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dto.UserDTO;
import org.junit.Assert;
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
public class GetUserByIdRequestTestIT extends AbstractControllerRequestTest {

    @Autowired
    public String mainURL;

    @Test
    @Override
    public void testStatusCode() {
        this.testGetStatusCode();
    }

    @Test
    @Override
    public void testMessageType() {
        this.testGetMessageType();
    }

    @Test
    @Override
    public void testPayload() {
        Object object = this.testGetPayload(UserDTO.class);
        if(object instanceof UserDTO) {
            UserDTO users = (UserDTO) object;
            Assert.assertEquals("Retrieved user has different ID", 1, users.id);
        }else{
            Assert.fail("Failed to parse response to " + UserDTO.class.getName());
        }
    }

    @Override
    public String getUrl() {
        return mainURL + "users/1";
    }
}
