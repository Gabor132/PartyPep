package com.gabor.partypeps.controllers;

import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.models.dto.MessageDTO;
import com.gabor.partypeps.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "messages")
@RequestMapping(path = "messages")
public class MessageController extends AbstractController<Message> {


    @Autowired
    public MessageService messageService;

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

    @Override
    public AbstractService getService() {
        return messageService;
    }
}
