package com.gabor.partypeps.services;

import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.mappers.GroupMapper;
import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.GroupDTO;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.repositories.GroupRepository;
import com.gabor.partypeps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService extends AbstractService<Group, GroupDTO> {


    private static GroupMapper groupMapper = new GroupMapper();


    @Autowired
    public GroupRepository groupRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserService userService;

    /**
     * Function to insert a new DTO Group object into the database
     *
     * @param dto GroupDTO
     * @return long as the newly added group's ID
     */
    @Override
    public long insert(GroupDTO dto) {
        Group group = groupMapper.mapToDAO(dto);
        for (String username : dto.usersUsernames) {
            User user = userRepository.findByUsername(username);
            group.getGroupUsers().add(user);
        }
        return groupRepository.save(group).getId();
    }

    /**
     * TODO - Function to update a Group Entity
     *
     * @param dto GroupDTO
     * @return boolean - to show if the update has succeeded of not
     */
    @Override
    public boolean update(GroupDTO dto) {
        return false;
    }

    /**
     * Function that using an User's id, will return all the groups to which he belongs to
     *
     * @param id Long
     * @return List<GroupDTO>
     */
    public List<GroupDTO> findGroupsOfAUser(Long id) {
        UserDTO user = userService.findById(id);
        if(user == null){
            return null;
        }
        List<Group> groups = new ArrayList<>();
        for (Long groupId : user.groupIds) {
            Optional<Group> group = groupRepository.findById(groupId);
            group.ifPresent(groups::add);
        }
        return groupMapper.mapListOfDTO(groups);
    }

    @Override
    public JpaRepository<Group, Long> getRepository() {
        return groupRepository;
    }

    @Override
    public AbstractMapper<Group, GroupDTO> getMapper() {
        return groupMapper;
    }
}
