package com.gabor.partypeps.services;

import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.mappers.EventMapper;
import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.EventDTO;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.repositories.EventRepository;
import com.gabor.partypeps.repositories.GroupRepository;
import com.gabor.partypeps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService extends AbstractService<Event, EventDTO>  {


    private static EventMapper eventMapper = new EventMapper();

    @Autowired
    public EventRepository eventRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public GroupRepository groupRepository;

    @Autowired
    public UserService userService;

    @Transactional
    public List<EventDTO> getUserEvents(String myUsername){
        UserDTO myself = userService.findMyselfByUsername(myUsername);
        return this.findAll().stream().filter(event -> event.subscribedUsers.contains(myUsername)).map(e -> e.setupActions(myself)).collect(Collectors.toList());
    }


    @Transactional
    public List<EventDTO> getAllEvents(String myUsername){
        UserDTO myself = userService.findMyselfByUsername(myUsername);
        List<Event> events = eventRepository.findAll();
        return events.stream().map(e -> eventMapper.mapToDTO(e).setupActions(myself)).collect(Collectors.toList());
    }

    @Transactional
    public EventDTO getEventById(String myUsername, Long eventId){
        UserDTO myself = userService.findMyselfByUsername(myUsername);
        Optional<Event> event = eventRepository.findById(eventId);
        return event.map(value -> eventMapper.mapToDTO(value).setupActions(myself)).orElse(null);
    }

    @Override
    public long insert(EventDTO dto) {
        Event event = eventMapper.mapToDAO(dto);
        for (String username : dto.subscribedUsers) {
            User user = userRepository.findByUsername(username);
            event.getSubscribers().add(user);
        }
        return eventRepository.save(event).getId();
    }

    @Transactional
    public long createEvent(String myUsername, EventDTO eventDTO){
        Event event = eventMapper.mapToDAO(eventDTO);
        event.setCreationUser(userRepository.findByUsername(myUsername));
        HashMap<String, User> invitees = new HashMap<>();
        if (eventDTO.subscribedUsers != null) {
            for (String invitation : eventDTO.subscribedUsers) {
                User user = userRepository.findByUsername(invitation);
                if (user == null) {
                    Group group = groupRepository.getGroupByName(invitation);
                    if (group != null) {
                        for (User groupUser : group.getGroupUsers()) {
                            invitees.put(groupUser.getUsername(), groupUser);
                        }
                    }
                } else {
                    invitees.put(user.getUsername(), user);
                }
            }
        }
        invitees.put(myUsername, userRepository.findByUsername(myUsername));
        for (User user : invitees.values()) {
            event.getSubscribers().add(user);
        }
        event = eventRepository.saveAndFlush(event);
        return event.getId();
    }

    @Transactional
    public Boolean subscribeToEvent(String username, Long eventId){
        User user = userRepository.findByUsername(username);
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()){
            Event theEvent = event.get();
            theEvent.getSubscribers().add(user);
            eventRepository.saveAndFlush(theEvent);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean unsubscribeToEvent(String username, Long eventId){
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()){
            Event theEvent = event.get();
            List<User> toRemove = theEvent.getSubscribers().stream().filter(u -> u.getUsername().equals(username)).collect(Collectors.toList());
            theEvent.getSubscribers().removeAll(toRemove);
            eventRepository.saveAndFlush(theEvent);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean deleteEvent(String username, Long eventId){
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()){
            Event theEvent = event.get();
            if(theEvent.getCreationUser().getUsername().equals(username)){
                theEvent.getSubscribers().clear();
            }
            theEvent = eventRepository.saveAndFlush(theEvent);
            eventRepository.deleteById(theEvent.getId());
            return true;
        }
        return false;
    }

    /**
     * TODO - Function to update a Event Entity
     * @param dto
     * @return
     */
    @Override
    public boolean update(EventDTO dto) {
        return false;
    }

    @Override
    public JpaRepository<Event, Long> getRepository() {
        return eventRepository;
    }

    @Override
    public AbstractMapper<Event, EventDTO> getMapper() {
        return eventMapper;
    }
}
