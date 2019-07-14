package com.gabor.unit.services;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.services.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * TODO
 */
@SpringBootTest(classes = {
        DatabaseConfig.class,
        EntityManagerFactoryConfig.class,
        RepositoryConfiguration.class,
        MapperTestConfiguration.class,
        ServiceTestConfiguration.class,
        UrlTestConfiguration.class
})
@ActiveProfiles(value = "DEV")
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageServiceTest {

    @Autowired
    public MessageService messageService;

    @Test
    public void insert() {
    }

    @Test
    public void update() {
    }

    @Test
    public void getRepository() {
        JpaRepository<Message, Long> repository = messageService.getRepository();
        Assert.notNull(repository, "The mapper returned by the service is null");
    }

    @Test
    public void getMapper() {
        AbstractMapper mapper = messageService.getMapper();
        Assert.notNull(mapper, "The mapper returned by the service is null");
    }
}