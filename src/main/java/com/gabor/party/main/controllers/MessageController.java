package com.gabor.party.main.controllers;

import com.gabor.party.common.controller.AbstractController;
import com.gabor.party.common.service.AbstractService;
import com.gabor.party.main.models.dao.Message;
import com.gabor.party.main.models.dto.MessageDTO;
import com.gabor.party.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "messages")
@RequestMapping(path = "messages")
public class MessageController extends AbstractController<Message> {


    @Autowired
    public MessageService messageService;

    @Override
    public AbstractService<MessageDTO> getService() {
        return messageService;
    }

    @RequestMapping(value = "/all", name = "getAllMesssages", method = RequestMethod.GET)
    public List<MessageDTO> getAllMessages() {
        return messageService.findAll();
    }

    @PostMapping(path = "/add")
    public Long addMessage(@RequestBody MessageDTO messageDto) {
        return messageService.insert(messageDto);
    }

    @DeleteMapping(path = "/remove/{id}")
    public boolean removeMessage(@PathVariable Long id) {
        return messageService.delete(id);
    }
}
