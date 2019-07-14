package com.gabor.integration.controllers;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dto.GroupDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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
public class GetGroupByIdRequestTestIT extends AbstractControllerRequestTest{

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
        Object object = this.defaultGetTestPayload(GroupDTO.class);
        Assert.notNull(object, "Object from parsing is null");
        GroupDTO group = (GroupDTO) object;
        Assert.notNull(group, "Object parsed is not a GroupDTO");
        Assert.isTrue(group.id == 1, "Id is mismatched");
    }

    @Override
    protected String getUrl() {
        return mainURL + "groups/1";
    }
}