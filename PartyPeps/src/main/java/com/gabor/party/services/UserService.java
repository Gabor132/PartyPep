package com.gabor.party.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabor.party.main.models.User;
import com.gabor.party.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;
	
	public List<User> findAll(){
		return (List<User>)userRepository.findAll();
	}
	
}
