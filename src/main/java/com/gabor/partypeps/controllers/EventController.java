package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.models.dto.EventDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<EventDTO> getAllEvents() {
        return eventService.findAll();
    }
}
