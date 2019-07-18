package com.gabor.integration.controllers;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

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
public class AddUserRequestTestIT extends AbstractControllerRequestTest{

    @Autowired
    public String mainURL;

    @Test
    @Override
    public void testStatusCode() {
        this.testPostStatusCode(getDTO());
    }

    @Test
    @Override
    public void testMessageType() { this.testPostMessageType(getDTO()); }

    @Test
    @Override
    public void testPayload() {
        this.testPostPayload(getDTO());
    }

    private UserDTO getDTO(){
        UserDTO user = new UserDTO();
        user.groupIds = new ArrayList<>();
        user.invitationIds = new ArrayList<>();
        user.name = "User de Test";
        user.password = "ceva parola acolo";
        return user;
    }

    @Override
    protected String getUrl() {
        return mainURL + "users/add";
    }
}
