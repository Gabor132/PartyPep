package com.gabor.party.main.controllers;

import java.util.List;

import com.gabor.party.main.models.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabor.party.main.models.User;
import com.gabor.party.services.UserService;

/**
 * This is the main controller for all services related with the User data
 * model. Note: This is a @RestController because the response should be a JSON
 * (basic data)
 * 
 * @author dragos.gabor
 *
 */
@RestController
public class UserController {

	@Autowired
	public UserService userService;

	@RequestMapping("users/all")
	public List<UserDTO> getAllUsers() {
		return userService.findAll();
	}

	@RequestMapping("users/{id}")
	public UserDTO getUser(@PathVariable Long id){
		return (UserDTO) userService.findById(id);
	}

	/**
	 * TODO
	@RequestMapping(value = "/add", params = "{userDTO}")
	public Long insertUser(UserDTO userDTO){
		return userService.insert(userDTO);
	}
	*/

}
