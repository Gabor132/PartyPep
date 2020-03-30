package com.gabor.unit.services;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.database.DatabaseConfiguration;
import com.gabor.partypeps.configurations.database.EntityManagerFactoryConfiguration;
import com.gabor.partypeps.configurations.database.RepositoryConfiguration;
import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.services.EventService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.logging.Logger;

@SpringBootTest(classes = {
        DatabaseConfiguration.class,
        EntityManagerFactoryConfiguration.class,
        RepositoryConfiguration.class,
        MapperTestConfiguration.class,
        ServiceTestConfiguration.class,
        UrlTestConfiguration.class
})
@ActiveProfiles(value = "DEV")
@RunWith(SpringJUnit4ClassRunner.class)
public class EventServiceTest {

    private static Logger logger = Logger.getLogger(EventServiceTest.class.toString());

    @Autowired
    public EventService eventService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }


    /**
     * TODO
     */
    @Test
    public void getUserEvents() {
    }

    /**
     * TODO
     */
    @Test
    public void insertTest() {
    }

    /**
     * TODO
     */
    @Test
    public void updateTest() {
    }

    @Test
    public void getRepositoryTest() {
        JpaRepository<Event, Long> repository = eventService.getRepository();
        Assert.notNull(repository, "The mapper returned by the service is null");
    }

    @Test
    public void getMapperTest() {
        AbstractMapper mapper = eventService.getMapper();
        Assert.notNull(mapper, "The mapper returned by the service is null");
    }

}