package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.models.dto.EventDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController(value = "events")
@RequestMapping(path = "events")
public class EventController extends AbstractController<Event> {

    @Autowired
    public EventService eventService;

    @Override
    public AbstractService getService() {
        return eventService;
    }

    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<EventDTO> getAllEvents(Principal principal) {
        return eventService.getAllEvents(principal.getName());
    }

    @GetMapping(path = "/myevents")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<EventDTO> getMyEvents(Principal principal){
        return eventService.getUserEvents(principal.getName());
    }

    @GetMapping(path = "/event/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public EventDTO getEvent(Principal principal, @PathVariable Long eventId){
        return eventService.getEventById(principal.getName(), eventId);
    }

    @PutMapping(path = "/subscribe")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean subscribeToEvent(Principal principal, @RequestBody String eventId) {
        return eventService.subscribeToEvent(principal.getName(), Long.parseLong(eventId));
    }

    @DeleteMapping(path = "/unsubscribe/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean unsubscribeToEvent(Principal principal, @PathVariable Long eventId) {
        return eventService.unsubscribeToEvent(principal.getName(), eventId);
    }

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long createEvent(Principal principal, @RequestBody EventDTO eventDto) {
        return eventService.createEvent(principal.getName(), eventDto);
    }


    @DeleteMapping(path = "/delete/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean deleteEvent(Principal principal, @PathVariable Long eventId) {
        return eventService.deleteEvent(principal.getName(), eventId);
    }
}
