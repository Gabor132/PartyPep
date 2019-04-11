package com.gabor.party.main.mappers;

import com.gabor.party.main.models.User;
import com.gabor.party.main.models.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static List<UserDTO> mapUsersOut(List<User> users){
        return users.stream().map((x -> UserMapper.mapUserToDTO(x))).collect(Collectors.toList());
    }

    public static List<User> mapUsersIn(List<UserDTO> usersDTO){
        return usersDTO.stream().map((x -> UserMapper.mapDTOToUser(x))).collect(Collectors.toList());
    }

    /**
     * For now we do not support simultanious changes to user, groups and invitations
     * Just the ID and name
     * TODO
     * @param userDTO
     * @return User
     */
    private static User mapDTOToUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.id);
        user.setName(userDTO.name);
        return user;
    }

    private static UserDTO mapUserToDTO(User user){
        return new UserDTO(user);
    }

}
