package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserDTO> getAllUsers(Principal principal) {
        return userService.findAllButNotMe(principal.getName());
    }

    @GetMapping(path = "/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO getUser(Principal principal, @PathVariable String username) {
        return userService.findUserByUsername(principal.getName(), username);
    }

    @GetMapping(path = "/peps")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserDTO> getFollowers(Principal principal) {
        return userService.findMyFriends(principal.getName());
    }

    @GetMapping(path = "/get_user_details")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO getUserDetails(Principal principal){
        return userService.findMyselfByUsername(principal.getName());
    }

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long insertUser(@RequestBody UserDTO userDTO) {
        return userService.insert(userDTO);
    }

    @PutMapping(path = "/follow")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean followUser(Principal principal, @RequestBody String followedUsername) {
        return userService.followUser(principal.getName(), followedUsername);
    }

    @DeleteMapping(path = "/unfollow/{followedUsername}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean unfollowUser(Principal principal, @PathVariable String followedUsername) {
        return userService.unfollowUser(principal.getName(), followedUsername);
    }

    @DeleteMapping(path = "/remove/myself/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean deleteUser(Principal principal, @PathVariable Long id) {
        return userService.suicide(principal.getName(), id);
    }

    @Override
    public AbstractService getService() {
        return userService;
    }
}
