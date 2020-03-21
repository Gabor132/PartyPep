package com.gabor.partypeps.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabor.partypeps.models.dao.Authority;
import com.gabor.partypeps.models.dao.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO extends AbstractDTO {

    public String name;

    public String email;

    public String password;

    public List<Long> groupIds;

    public List<Long> invitationIds;

    @JsonIgnore
    public List<Authority> authorities;

    public UserDTO() {
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.invitationIds = user.getInvitations().stream().map(x -> x.getId()).collect(Collectors.toList());
        this.groupIds = user.getGroups().stream().map(x -> x.getId()).collect(Collectors.toList());
        this.authorities = user.getAuthorities().stream().filter(x -> x instanceof Authority).map(x -> (Authority) x).collect(Collectors.toCollection(ArrayList::new));
    }

    public UserDTO mutePassword(){
        this.password = null;
        return this;
    }

}
