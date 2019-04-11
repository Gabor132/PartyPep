package com.gabor.party.main.models.dto;

import com.gabor.party.main.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO implements AbstractDTO{

    public long id;

    public String name;

    public List<Long> groupIds;

    public List<Long> invitationids;

    public UserDTO(){ }

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.invitationids = user.getInvitations().stream().map(x -> x.getId()).collect(Collectors.toList());
        this.groupIds = user.getGroups().stream().map(x -> x.getId()).collect(Collectors.toList());
    }

}
