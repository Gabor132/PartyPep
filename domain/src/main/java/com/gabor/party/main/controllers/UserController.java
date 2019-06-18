package com.gabor.party.main.controllers;

import com.gabor.party.main.models.dao.User;
import com.gabor.party.main.models.dto.UserDTO;
import com.gabor.party.services.MessageService;
import com.gabor.party.services.UserService;
import common.controller.AbstractController;
import common.service.AbstractService;
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
@RestController
public class UserController extends AbstractController<User> {

    @Autowired
    public UserService userService;

    @Autowired
    public MessageService messageService;

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @RequestMapping("users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return (UserDTO) userService.findById(id);
    }

    public AbstractService getService() {
        return userService;
    }

    @PostMapping(path = "users/add")
    public Long insertUser(@RequestBody UserDTO userDTO) {
        return userService.insert(userDTO);
    }

    @DeleteMapping(path = "users/remove/{id}")
    public Boolean deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }

}
