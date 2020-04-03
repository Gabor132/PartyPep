package com.gabor.partypeps.models.dto;

import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.models.dao.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class EventDTO extends AbstractDTO{

    public String name;

    public String startOfEvent;

    public String location;

    public List<String> subscribedUsers;

    public EventDTO(){}

    public EventDTO(Event event){
        this.id = event.getId();
        this.location = event.getLocation();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm");
        this.startOfEvent = dateFormat.format(event.getStartOfEvent());
        this.name = event.getName();
        this.subscribedUsers = event.getSubscribers().stream().map(User::getUsername).collect(Collectors.toList());
    }

}
