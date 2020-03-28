package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.Message;
import com.gabor.partypeps.models.dto.MessageDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController(value = "messages")
@RequestMapping(path = "messages")
public class MessageController extends AbstractController<Message> {

    @Autowired
    public MessageService messageService;

    @GetMapping(path = "/private/{withRead}")
    @ResponseBody
    public List<MessageDTO> getPrivateMessages(Principal principal, @PathVariable Boolean withRead) {
        return messageService.findMyPrivateMessages(principal.getName(), withRead);
    }

    @GetMapping(path = "/group/{withRead}")
    @ResponseBody
    public List<MessageDTO> getGroupMessages(Principal principal, @PathVariable Boolean withRead) {
        return messageService.findMyGroupMessages(principal.getName(), withRead);
    }

    @GetMapping(path = "/all/{withRead}")
    @ResponseBody
    public List<MessageDTO> getAllMessages(Principal principal, @PathVariable Boolean withRead) {
        return messageService.findAllMyMessages(principal.getName(), withRead);
    }

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long addMessage(@RequestBody MessageDTO messageDto) {
        return messageService.insert(messageDto);
    }

    @PostMapping(path = "/read")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Integer readMessages(Principal principal, @RequestBody List<Long> ids){
        return messageService.readMessages(principal.getName(), ids);
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
