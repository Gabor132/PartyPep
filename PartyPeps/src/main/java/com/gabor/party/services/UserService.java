package com.gabor.party.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabor.party.main.models.User;
import com.gabor.party.mappers.UserMapper;
import com.gabor.party.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;

	@Transactional
	public List<User> findAll() {
		List<User> users = (List<User>) userRepository.findAll();
		return UserMapper.mapUsersOut(users);
	}

}
