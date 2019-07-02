package com.gabor.unit.services;

import com.gabor.common.AbstractTest;
import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.services.GroupService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        DatabaseConfig.class,
        EntityManagerFactoryConfig.class,
        RepositoryConfiguration.class,
        MapperTestConfiguration.class,
        ServiceTestConfiguration.class
})
@Ignore
public class GroupServiceTest extends AbstractTest {

    @Autowired
    public GroupService groupService;

    @Test
    public void insert() {
    }

    @Test
    public void update() {
    }

    @Test
    public void findGroupsOfUser() {
    }

    @Test
    public void getRepository() {
        JpaRepository<Group, Long> repository = groupService.getRepository();
        Assert.notNull(repository, "The mapper returned by the service is null");
    }

    @Test
    public void getMapper() {
        AbstractMapper mapper = groupService.getMapper();
        Assert.notNull(mapper, "The mapper returned by the service is null");
    }
}