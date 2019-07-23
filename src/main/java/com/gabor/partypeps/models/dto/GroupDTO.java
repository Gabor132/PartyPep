package com.gabor.partypeps.models.dto;

import com.gabor.partypeps.models.dao.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupDTO extends AbstractDTO {

    public String name;

    public List<Long> userIds;

    public GroupDTO() {
        userIds = new ArrayList<>();
    }

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.userIds = group.getGroupUsers().stream().map(x -> x.getId()).collect(Collectors.toList());
    }

}
