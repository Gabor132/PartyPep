package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream().map(UserDTO::mutePassword).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO getUser(@PathVariable Long id) {
        UserDTO foundUser = (UserDTO) userService.findById(id);
        if(foundUser != null){
            foundUser = foundUser.mutePassword();
        }
        return foundUser;
    }

    @PostMapping(path = "/get_user_details")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO getUserDetails(@RequestBody UserDTO userDTO){
        UserDTO foundUser = (UserDTO) userService.findUserByUsername(userDTO.name);
        if(foundUser != null){
            foundUser = foundUser.mutePassword();
        }
        return foundUser;
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
