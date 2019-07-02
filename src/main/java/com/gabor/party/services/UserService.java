package com.gabor.party.services;

import com.gabor.party.common.dto.AbstractDTO;
import com.gabor.party.common.service.AbstractService;
import com.gabor.party.main.mappers.UserMapper;
import com.gabor.party.main.models.dao.User;
import com.gabor.party.main.models.dto.UserDTO;
import com.gabor.party.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends AbstractService {

    @Autowired
    public UserRepository userRepository;

    public static UserMapper userMapper = new UserMapper();

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.mapListOfDTO(users);
    }

    /**
     * TODO: Check if it is present first
     *
     * @param id
     * @return
     */
    @Override
    public AbstractDTO findById(Long id) {
        User foundUser = userRepository.findById(id).get();
        return (foundUser != null) ? new UserDTO(foundUser) : new UserDTO();
    }

    @Override
    public long insert(AbstractDTO dto) {
        UserDTO userDTO = (UserDTO) dto;
        List<UserDTO> list = new ArrayList<>();
        list.add(userDTO);
        return userRepository.save(userMapper.mapListOfDAO(list).get(0)).getId();
    }

    /**
     * TODO
     *
    @Query(value = "")
    public User findbySomething(){

    }
    **/


    /**
     * Function used to remove a User entry from the database
     * @param id
     * @return Boolean
     */
    @Override
    public boolean delete(Long id) {
        return UserService.genericDelete(id, userRepository);
    }

    /**
     * TODO
     *
     * @param dto
     * @return
     */
    @Override
    public boolean update(AbstractDTO dto) {
        return false;
    }

}
