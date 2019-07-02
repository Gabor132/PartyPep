package com.gabor.party.main.controllers;

import com.gabor.party.common.controller.AbstractController;
import com.gabor.party.common.service.AbstractService;
import com.gabor.party.main.models.dao.User;
import com.gabor.party.main.models.dto.UserDTO;
import com.gabor.party.services.MessageService;
import com.gabor.party.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the main controller for all services related with the User data
 * model.
 * (basic data)
 *
 * @author dragos.gabor
 */
@RestController(value = "users")
@RequestMapping(path = "users")
public class UserController extends AbstractController<User> {

    @Autowired
    public UserService userService;

    @Autowired
    public MessageService messageService;

    public AbstractService getService() {
        return userService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @RequestMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return (UserDTO) userService.findById(id);
    }


    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public Long insertUser(@RequestBody UserDTO userDTO) {
        return userService.insert(userDTO);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/remove/{id}")
    public Boolean deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }

}
