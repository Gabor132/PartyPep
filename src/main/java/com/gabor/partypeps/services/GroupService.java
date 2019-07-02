package com.gabor.partypeps.services;

import com.gabor.partypeps.models.dto.AbstractDTO;
import com.gabor.partypeps.mappers.GroupMapper;
import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.GroupDTO;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.repositories.GroupRepository;
import com.gabor.partypeps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService extends AbstractService {


    public static GroupMapper groupMapper = new GroupMapper();


    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public List<GroupDTO> findAll() {
        List<Group> groups = groupRepository.findAll();
        return groupMapper.mapListOfDTO(groups);
    }

    /**
     * TODO: Check if group is present first
     *
     * @param id
     * @return
     */
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
        Group group = groupMapper.mapToDAO(groupDTO);
        for(Long userId : groupDTO.userIds){
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                group.getGroupUsers().add(user.get());
            }
        }
        return groupRepository.save(group).getId();
    }

    /**
     * TODO
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        return genericDelete(id, groupRepository);
    }

    /**
     * TODO
     *
     * @param dto
     * @return
     */
    @Override
    public boolean update(AbstractDTO dto) {
        return false;
    }

    /**
     * Function that using an User's id, will return all the groups to which he belongs to
     * @param id
     * @return
     */
    public List<GroupDTO> findGroupsOfUser(Long id) {
        UserDTO user = (UserDTO) userService.findById(id);
        List<Group> groups = new ArrayList<>();
        for(Long groupId : user.groupIds){
            Optional<Group> group = groupRepository.findById(groupId);
            if(group.isPresent()){
                groups.add(group.get());
            }
        }
        return groupMapper.mapListOfDTO(groups);
    }
}
