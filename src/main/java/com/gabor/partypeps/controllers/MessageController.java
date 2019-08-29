package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.models.dto.MessageDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "messages")
@RequestMapping(path = "messages")
public class MessageController extends AbstractController<Message> {

    @Autowired
    public MessageService messageService;

    @GetMapping(path = "/all")
    @ResponseBody
    public List<MessageDTO> getAllMessages() {
        return messageService.findAll();
    }

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long addMessage(@RequestBody MessageDTO messageDto) {
        return messageService.insert(messageDto);
    }

    @DeleteMapping(path = "/remove/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean removeMessage(@PathVariable Long id) {
        return messageService.delete(id);
    }

    @Override
    public AbstractService getService() {
        return messageService;
    }
}
