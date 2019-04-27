package com.gabor.party.services;

import com.gabor.party.main.mappers.GroupMapper;
import com.gabor.party.main.models.dao.Group;
import com.gabor.party.main.models.dao.User;
import com.gabor.party.main.models.dto.AbstractDTO;
import com.gabor.party.main.models.dto.GroupDTO;
import com.gabor.party.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService implements AbstractService{


    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    @Override
    public List<GroupDTO> findAll() {
        List<Group> groups = (List<Group>) groupRepository.findAll();
        return GroupMapper.mapGroupsOut(groups);
    }

    @Override
    public AbstractDTO findById(Long id) {
        Group group = (Group) groupRepository.findById(id).get();
        return new GroupDTO(group);
    }

    @Override
    public long insert(AbstractDTO dto) {

        GroupDTO groupDTO = (GroupDTO) dto;
        List<GroupDTO> list = new ArrayList<GroupDTO>();
        list.add(groupDTO);
        return groupRepository.save(GroupMapper.mapGroupsIn(list).get(0)).getId();
    }

    /**
     * TODO
     * @param dto
     * @return
     */
    @Override
    public boolean delete(AbstractDTO dto) {
        return false;
    }

    /**
     * TODO
     * @param dto
     * @return
     */
    @Override
    public boolean update(AbstractDTO dto) {
        return false;
    }

    public List<GroupDTO> findGroupsOfUser(Long id){
        User user = (User) userService.findById(id);
        return GroupMapper.mapGroupsOut(user.getGroups());
    }
}
