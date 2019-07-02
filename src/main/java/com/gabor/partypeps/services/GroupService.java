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

    /**
     * Function used to retrieve all the Group Entities from the database
     * @return
     */
    @Override
    public List<GroupDTO> findAll() {
        List<Group> groups = groupRepository.findAll();
        return groupMapper.mapListOfDTO(groups);
    }

    /**
     * Function to retrieve a Group entity from the database with a specific ID
     *
     * @param id
     * @return AbstractDTO (data transfer object to be used in the REST call response)
     */
    @Override
    public AbstractDTO findById(Long id) {
        Group group = (Group) groupRepository.findById(id).get();
        return new GroupDTO(group);
    }

    /**
     * Function to insert a new DTO Group object into the database
     * @param dto
     * @return long as the newly added group's ID
     */
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
     * Function to delete a Group entity from the database using it's ID
     *
     * @param id
     * @return boolean to determine if the delete as taken place
     */
    @Override
    public boolean delete(Long id) {
        return genericDelete(id, groupRepository);
    }

    /**
     * TODO - Method to update a Group Entity
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
