package com.gabor.partypeps.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabor.partypeps.models.dao.Authority;
import com.gabor.partypeps.models.dao.Event;
import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dao.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO extends AbstractDTO {

    public String name;

    public String email;

    @JsonIgnore
    public String password;

    public List<String> followers;

    public List<String> following;

    public List<Long> groupIds;

    public List<Long> subscriptions;

    @JsonIgnore
    public List<Authority> authorities;

    public Boolean canFollow = false;

    public Boolean canMessage = false;

    public Boolean canViewProfile = false;

    public UserDTO() {
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.followers = user.getFollowers().stream().map(follow -> follow.getFollower().getUsername()).collect(Collectors.toList());
        this.following = user.getFollowing().stream().map(follow -> follow.getFollowed().getUsername()).collect(Collectors.toList());
        this.subscriptions = user.getSubscriptions().stream().map(Event::getId).collect(Collectors.toList());
        this.groupIds = user.getGroups().stream().map(Group::getId).collect(Collectors.toList());
        this.authorities = user.getAuthorities().stream().filter(x -> x instanceof Authority).map(x -> (Authority) x).collect(Collectors.toCollection(ArrayList::new));
    }


    public static Boolean areFriends(UserDTO him, UserDTO me){
        return him.followers.contains(me.name) && him.following.contains(me.name);
    }

    public static Boolean AFollowsB(UserDTO A, UserDTO B){
        return A.following.contains(B.name);
    }

    public UserDTO setupActions(UserDTO myself){
        if(UserDTO.areFriends(myself, this)){
            this.canMessage = true;
            this.canViewProfile = true;
        }
        if(! UserDTO.AFollowsB(myself, this)){
            this.canFollow = true;
        }
        return this;
    }

}
