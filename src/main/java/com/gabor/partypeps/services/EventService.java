package com.gabor.partypeps.services;

import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.mappers.EventMapper;
import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.EventDTO;
import com.gabor.partypeps.repositories.EventRepository;
import com.gabor.partypeps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService extends AbstractService<Event, EventDTO>  {


    private static EventMapper eventMapper = new EventMapper();

    @Autowired
    public EventRepository eventRepository;

    @Autowired
    public UserRepository userRepository;

    @Transactional
    public List<EventDTO> getUserEvents(String username){
        return this.findAll().stream().filter(event -> event.subscribedUsers.contains(username)).collect(Collectors.toList());
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

    /**
     * TODO
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
