package com.gabor.partypeps.mappers;

import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.models.dto.EventDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class EventMapper extends AbstractMapper<Event, EventDTO>{


    @Override
    public EventDTO mapToDTO(Event entity) {
        return new EventDTO(entity);
    }

    @Override
    public Event mapToDAO(EventDTO dto) {
        Event newEvent = new Event();
        if(dto.id != null){
            newEvent.setId(dto.id);
        }
        newEvent.setName(dto.name);
        newEvent.setLocation(dto.location);
        newEvent.setSubscribers(new LinkedList<>());
        try {
            newEvent.setStartOfEvent(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(dto.startOfEvent));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        /**
         * TODO: SET INVITEES
         * newEvent.setInvitees =....
         */
        return newEvent;
    }
}
