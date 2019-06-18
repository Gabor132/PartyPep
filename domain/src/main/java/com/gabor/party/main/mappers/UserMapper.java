package com.gabor.party.main.mappers;

import com.gabor.party.main.models.dao.User;
import com.gabor.party.main.models.dto.UserDTO;
import common.mapper.AbstractMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper extends AbstractMapper<User, UserDTO> {


    @Override
    public UserDTO mapToDTO(User entity) {
        return new UserDTO(entity);
    }

    /**
     * For now we do not support simultanious changes to user, groups and invitations
     * Just the ID and name
     * @param dto
     * @return User
     */
    @Override
    public User mapToDAO(UserDTO dto) {
        User user = new User();
        user.setId(dto.id);
        user.setName(dto.name);
        user.setPassword(dto.password);
        return user;
    }

}
