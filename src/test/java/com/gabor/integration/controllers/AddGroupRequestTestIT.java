package com.gabor.integration.controllers;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dto.AbstractDTO;
import com.gabor.partypeps.models.dto.GroupDTO;
import org.junit.Assert;
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
public class AddGroupRequestTestIT extends AbstractControllerRequestTest{

    @Autowired
    public String mainURL;

    private AbstractDTO getDto(){
        GroupDTO group = new GroupDTO();
        group.name = "Banana";
        group.userIds = new ArrayList<>();
        group.userIds.add(1L);
        group.userIds.add(2L);
        return group;
    }

    @Test
    @Override
    public void testStatusCode() {
        this.defaultPostTestStatusCode(getDto());
    }

    @Test
    @Override
    public void testMessageType() {
        this.defaultPostTestMessageType(getDto());
    }

    @Test
    @Override
    public void testPayload() {
        boolean isSuccessful = this.defaultPostTestPayload(GroupDTO.class, getDto());
        Assert.assertTrue("Addition of new Group failed", isSuccessful);
    }

    @Override
    protected String getUrl() {
        return mainURL = "groups/add";
    }
}
