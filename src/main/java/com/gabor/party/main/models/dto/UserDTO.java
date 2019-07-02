package com.gabor.party.main.models.dto;

import com.gabor.party.common.dto.AbstractDTO;
import com.gabor.party.main.models.dao.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO extends AbstractDTO {

    public long id;

    public String name;

    public String password;

    public List<Long> groupIds;

    public List<Long> invitationIds;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.invitationIds = user.getInvitations().stream().map(x -> x.getId()).collect(Collectors.toList());
        this.groupIds = user.getGroups().stream().map(x -> x.getId()).collect(Collectors.toList());
    }

}
