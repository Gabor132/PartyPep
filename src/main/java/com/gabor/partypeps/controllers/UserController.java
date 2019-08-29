package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(path = "/all")
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public UserDTO getUser(@PathVariable Long id) {
        return (UserDTO) userService.findById(id);
    }

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long insertUser(@RequestBody UserDTO userDTO) {
        return userService.insert(userDTO);
    }

    @DeleteMapping(path = "/remove/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }

    @Override
    public AbstractService getService() {
        return userService;
    }
}
