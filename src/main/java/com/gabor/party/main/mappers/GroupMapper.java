package com.gabor.party.main.mappers;

import com.gabor.party.common.mapper.AbstractMapper;
import com.gabor.party.main.models.dao.Group;
import com.gabor.party.main.models.dto.GroupDTO;

public class GroupMapper extends AbstractMapper<Group, GroupDTO> {



    @Override
    public GroupDTO mapToDTO(Group entity) {
        return new GroupDTO(entity);
    }

    @Override
    public Group mapToDAO(GroupDTO dto) {
        Group newGroup = new Group();
        if(dto.id != null) {
            newGroup.setId(dto.id);
        }
        newGroup.setName(dto.name);
        /**
         * TODO later
         *
         newGroup = dto.userIds;
         **/
        return newGroup;
    }
}
