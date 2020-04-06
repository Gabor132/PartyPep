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

    public String creationUser;

    public List<String> subscribedUsers;

    public boolean canSubscribe;

    public boolean canEdit;

    public boolean canShare;

    public boolean canDelete;

    public EventDTO(){}

    public EventDTO(Event event){
        this.id = event.getId();
        this.location = event.getLocation();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm");
        this.startOfEvent = dateFormat.format(event.getStartOfEvent());
        this.creationUser = event.getCreationUser().getUsername();
        this.name = event.getName();
        this.subscribedUsers = event.getSubscribers().stream().map(User::getUsername).collect(Collectors.toList());
    }

    private static boolean isSubscribed(UserDTO myself, EventDTO event) {
        return event.subscribedUsers.contains(myself.name);
    }

    public EventDTO setupActions(UserDTO myself){
        this.canSubscribe =! EventDTO.isSubscribed(myself, this);
        this.canShare = true;
        this.canDelete = myself.name.equals(this.creationUser);
        return this;
    }

}
