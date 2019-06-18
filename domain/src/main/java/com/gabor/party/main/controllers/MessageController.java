package com.gabor.party.main.controllers;

import com.gabor.party.main.models.dao.Message;
import com.gabor.party.main.models.dto.MessageDTO;
import com.gabor.party.services.MessageService;
import common.controller.AbstractController;
import common.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController extends AbstractController<Message> {


    @Autowired
    public MessageService messageService;

    @Override
    public AbstractService getService() {
        return messageService;
    }

    @RequestMapping(value = "messages", name = "getAllMesssages", method = RequestMethod.GET)
    public List<MessageDTO> getAllMessages() {
        return messageService.findAll();
    }

    @PostMapping(path = "messages/add")
    public Long addMessage(@RequestBody MessageDTO messageDto) {
        return messageService.insert(messageDto);
    }

    @DeleteMapping(path = "messages/remove/{id}")
    public boolean removeMessage(@PathVariable Long id) {
        return messageService.delete(id);
    }
}
