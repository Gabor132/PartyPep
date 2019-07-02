package com.gabor.unit.services;

import com.gabor.common.AbstractTest;
import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.AbstractEntity;
import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.services.MessageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        DatabaseConfig.class,
        EntityManagerFactoryConfig.class,
        RepositoryConfiguration.class,
        MapperTestConfiguration.class,
        ServiceTestConfiguration.class
})
public class MessageServiceTest extends AbstractTest {

    @Autowired
    public MessageService messageService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

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