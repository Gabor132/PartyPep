package com.gabor.unit.services;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.database.DatabaseConfiguration;
import com.gabor.partypeps.configurations.database.EntityManagerFactoryConfiguration;
import com.gabor.partypeps.configurations.database.RepositoryConfiguration;
import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.models.dto.EventDTO;
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

import java.util.LinkedList;
import java.util.List;
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
     * Test to check the retrieval of a users events
     */
    @Test
    public void getUserEvents() {
        String myUsername = "Dani";
        List<EventDTO> events = eventService.getUserEvents(myUsername);
        Assert.isTrue(events.size() == 3, "User should've been subscribed only to three events");
        for(EventDTO event: events){
            Assert.isTrue(! event.canSubscribe, "User can subscribe to an already subscribed event");
            Assert.isTrue(event.canShare, "User cannot share event");
            Assert.isTrue(event.creationUser.equals(myUsername) == event.canDelete, "User can delete an event he did not create");
        }
    }

    /**
     * Test to check the retrieval of a users events
     */
    @Test
    public void getAllEventsTest() {
        String myUsername = "Dani";
        List<EventDTO> events = eventService.getUserEvents(myUsername);
        Assert.isTrue(events.size() > 0, "There should be some events, come on");
        for(EventDTO event: events){
            Assert.isTrue(event.subscribedUsers.contains(myUsername) != event.canSubscribe, "User can subscribe to an already subscribed event or cannot subscribe to an event he hasn't already subscribed");
            Assert.isTrue(event.canShare, "User cannot share event");
            Assert.isTrue(event.creationUser.equals(myUsername) == event.canDelete, "User can delete an event he did not create");
        }
    }


    /**
     * Test to check the retrieval of an event by ID
     */
    @Test
    public void getEventByIdTest() {
        String myUsername = "admin";
        long existentEventId = 1L;
        long nonExistentEventId = 51L;
        EventDTO event = eventService.getEventById(myUsername, existentEventId);
        Assert.isTrue(event != null, String.format("Event with id should exist", existentEventId));
        event = eventService.getEventById(myUsername, nonExistentEventId);
        Assert.isTrue(event == null, String.format("Event with id should not exist", nonExistentEventId));
    }

    /**
     * Test to check the insertion of new Events into the databse
     */
    @Test
    public void createEventTest() {
        String myUsername = "Tricolor";
        EventDTO event = new EventDTO();
        event.name = "La banane";
        String originalDate = "2020-01-08 20:20:00.0";
        event.startOfEvent = originalDate;
        event.location = "La un bar";
        List<String> users = new LinkedList<>();
        users.add("Costel");
        users.add("Vasile");
        event.subscribedUsers = users;
        long id = eventService.createEvent(myUsername, event);
        EventDTO loadedEvent = eventService.findById(id);
        Assert.isTrue(id > 0L, "Event was not created");
        Assert.isTrue(loadedEvent.location.equals("La un bar"), "Created event data is not equal with what was originally in the DTO");
        Assert.isTrue(loadedEvent.startOfEvent.equals(originalDate), "Created event data is not equal with what was originally in the DTO");
        Assert.isTrue(loadedEvent.creationUser.equals(myUsername), "Created event data is not equal with what was originally in the DTO");
        Assert.isTrue(loadedEvent.name.equals("La banane"), "Created event data is not equal with what was originally in the DTO");
        Assert.isTrue(loadedEvent.subscribedUsers.stream().filter(users
                ::contains).count() == 2, "Created event data is not equal with what was originally in the DTO");
    }

    /**
     * Test the subscribe action of an event
     */
    @Test
    public void subscribeEventTest(){
        String myUsername = "admin";
        long eventId = 4L;
        EventDTO event = eventService.getEventById(myUsername, eventId);
        Assert.isTrue(event.canSubscribe, "User should be able to subscribe");
        Assert.isTrue( event.canShare, "User always be able to share");
        Assert.isTrue(! event.canDelete, "User should not be able to delete the event");
        Boolean result = eventService.subscribeToEvent(myUsername, eventId);
        Assert.isTrue(result, "Failed to subscribe the user to event");
        event = eventService.getEventById(myUsername, eventId);
        Assert.isTrue(! event.canSubscribe, "User should not be able to subscribe");
        Assert.isTrue( event.canShare, "User always be able to share");
        Assert.isTrue(! event.canDelete, "User should not be able to delete the event");
    }

    /**
     * Test the unsubscribe action of an event
     */
    @Test
    public void unsubscribeEventTest(){
        String myUsername = "admin";
        long eventId = 3L;
        EventDTO event = eventService.getEventById(myUsername, eventId);
        Assert.isTrue(! event.canSubscribe, "User should not be able to subscribe");
        Assert.isTrue( event.canShare, "User always be able to share");
        Assert.isTrue(! event.canDelete, "User should not be able to delete the event");
        Boolean result = eventService.unsubscribeToEvent(myUsername, eventId);
        Assert.isTrue(result, "Failed to unsubscribe the user to event");
        event = eventService.getEventById(myUsername, eventId);
        Assert.isTrue(event.canSubscribe, "User should be able to subscribe");
        Assert.isTrue( event.canShare, "User always be able to share");
        Assert.isTrue(! event.canDelete, "User should not be able to delete the event");
    }

    /**
     * Test the deletion of events
     */
    @Test
    public void deleteEventTest(){
        String myUsername = "admin";
        long eventId = 5L;
        EventDTO event = eventService.getEventById(myUsername, eventId);
        Assert.isTrue(event.canSubscribe, "User should be able to subscribe");
        Assert.isTrue( event.canShare, "User always be able to share");
        Assert.isTrue(event.canDelete, "User should be able to delete the event");
        Boolean result = eventService.deleteEvent(myUsername, eventId);
        Assert.isTrue(result, "Failed to delete the event");
        event = eventService.getEventById(myUsername, eventId);
        Assert.isTrue(event == null, "Event shouldn't exist anymore");
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