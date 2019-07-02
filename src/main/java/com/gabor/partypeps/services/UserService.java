package com.gabor.partypeps.services;

import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.AbstractEntity;
import com.gabor.partypeps.models.dto.AbstractDTO;
import com.gabor.partypeps.mappers.UserMapper;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends AbstractService<User, UserDTO> {

    private static UserMapper userMapper = new UserMapper();

    @Autowired
    public UserRepository userRepository;

    /**
     * Function to insert a new User entity into the database
     * @param dto UserDTO
     * @return long the ID of the newly created entity
     */
    @Override
    public long insert(UserDTO dto) {
        UserDTO userDTO = dto;
        List<UserDTO> list = new ArrayList<>();
        list.add(userDTO);
        return userRepository.save(userMapper.mapListOfDAO(list).get(0)).getId();
    }

    /**
     * TODO - Function to update an existing User entity inside the database
     *
     * @param dto UserDTO
     * @return boolean
     */
    @Override
    public boolean update(UserDTO dto) {
        return false;
    }

    @Override
    public JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    public AbstractMapper<User, UserDTO> getMapper() {
        return userMapper;
    }

}
