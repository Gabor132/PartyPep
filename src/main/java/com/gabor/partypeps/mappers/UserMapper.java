package com.gabor.partypeps.mappers;

import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDTO> {

    @Override
    public UserDTO mapToDTO(User entity) {
        return new UserDTO(entity);
    }

    /**
     * For now we do not support simultanious changes to user, groups and invitations
     * Just the ID and name
     *
     * @param dto
     * @return User
     */
    @Override
    public User mapToDAO(UserDTO dto) {
        User user = new User();
        if (dto.id != null) {
            user.setId(dto.id);
        }
        user.setUsername(dto.name);
        user.setPassword(dto.password);
        user.setEmail(dto.email);
        return user;
    }

}
