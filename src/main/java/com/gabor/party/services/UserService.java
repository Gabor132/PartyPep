package com.gabor.party.services;

import java.util.ArrayList;
import java.util.List;

import com.gabor.party.main.models.dto.AbstractDTO;
import com.gabor.party.main.models.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabor.party.main.models.dao.User;
import com.gabor.party.main.mappers.UserMapper;
import com.gabor.party.repositories.UserRepository;

@Service
public class UserService implements AbstractService{

	@Autowired
	public UserRepository userRepository;

	@Override
	public List<UserDTO> findAll() {
		List<User> users = (List<User>) userRepository.findAll();
		return UserMapper.mapUsersOut(users);
	}

	@Override
	public AbstractDTO findById(Long id) {
		User foundUser = userRepository.findById(id).get();
		return (foundUser != null) ? new UserDTO(foundUser) : new UserDTO();
	}

	@Override
	public long insert(AbstractDTO dto) {
		UserDTO userDTO = (UserDTO) dto;
		List<UserDTO> list = new ArrayList<UserDTO>();
		list.add(userDTO);
		return userRepository.save(UserMapper.mapUsersIn(list).get(0)).getId();
	}

    /**
     * TODO
     * @param dto
     * @return
     */
	@Override
	public boolean delete(AbstractDTO dto) {
		return false;
	}

    /**
     * TODO
     * @param dto
     * @return
     */
	@Override
	public boolean update(AbstractDTO dto) {
		return false;
	}

}
