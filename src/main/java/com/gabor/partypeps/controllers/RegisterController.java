package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This is the main controller for all services related with Registering a user
 * (basic data)
 *
 * @author dragos.gabor
 */
@RestController(value = "register")
@RequestMapping(path = "register")
public class RegisterController extends AbstractController<User> {

    @Autowired
    public UserService userService;

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long insertUser(@RequestBody UserDTO userDTO) {
        return userService.insert(userDTO);
    }

    @PostMapping(path = "/checkUsername")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean checkUser(@RequestBody UserDTO user){
        UserDTO foundUser = userService.findMyselfByUsername(user.name);
        return foundUser == null;
    }

    @PostMapping(path = "/checkEmail")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean checkEmail(@RequestBody UserDTO user){
        UserDTO foundUser = userService.findUserByEmail(user.email);
        return foundUser == null;
    }

    @Override
    public AbstractService getService() {
        return userService;
    }
}
