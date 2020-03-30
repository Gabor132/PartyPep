package com.gabor.partypeps.mappers;

import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.models.dto.EventDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm");
        try {
            newEvent.setStartOfEvent(dateFormat.parse(dto.startOfEvent));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /**
         * TODO: SET INVITEES
         * newEvent.setInvitees =....
         */
        return newEvent;
    }
}
