package com.gabor.party.main.models.dto;

import com.gabor.party.main.models.dao.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupDTO implements AbstractDTO {

    public Long id;

    public String name;

    public List<Long> userIds;


    public GroupDTO(){}

    public GroupDTO(Group group){
        this.id = group.getId();
        this.name = group.getName();
        this.userIds = group.getGroupUsers().stream().map(x -> x.getId()).collect(Collectors.toList());
    }

}
