package com.gabor.partypeps.mappers;

import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dto.GroupDTO;
import org.springframework.stereotype.Component;

@Component
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
