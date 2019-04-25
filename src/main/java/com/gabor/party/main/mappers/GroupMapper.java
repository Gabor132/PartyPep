package com.gabor.party.main.mappers;

import com.gabor.party.main.models.dao.Group;
import com.gabor.party.main.models.dto.GroupDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static List<GroupDTO> mapGroupsOut(List<Group> groups){
        return groups.stream().map(x -> GroupMapper.mapGroupToDTO(x)).collect(Collectors.toList());
    }


    public static List<Group> mapGroupsIn(List<GroupDTO> groups){
        return groups.stream().map(x -> GroupMapper.mapDTOToGroup(x)).collect(Collectors.toList());
    }

    private static GroupDTO mapGroupToDTO(Group group){
        return new GroupDTO(group);
    }

    private static Group mapDTOToGroup(GroupDTO dto){
        Group newGroup = new Group();
        newGroup.setId(dto.id);
        newGroup.setName(dto.name);
        /**
         * TODO later
         *
        newGroup = dto.userIds;
         **/
        return newGroup;
    }

}
