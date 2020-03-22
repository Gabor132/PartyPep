package com.gabor.partypeps.models.dto;

import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dao.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupDTO extends AbstractDTO {

    public String name;

    public List<String> users_usernames;

    public GroupDTO() {
        users_usernames = new ArrayList<>();
    }

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.users_usernames = group.getGroupUsers().stream().map(User::getUsername).collect(Collectors.toList());
    }

}
