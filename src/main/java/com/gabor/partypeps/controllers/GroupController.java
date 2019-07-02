package com.gabor.partypeps.controllers;

import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dto.GroupDTO;
import com.gabor.partypeps.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "groups")
@RequestMapping(path = "groups")
public class GroupController extends AbstractController<Group> {

    @Autowired
    public GroupService groupService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<GroupDTO> getAllGroups() {
        return groupService.findAll();
    }

    @RequestMapping("/{id}")
    public GroupDTO getGroupById(@RequestParam Long id) {
        return (GroupDTO) groupService.findById(id);
    }

    @RequestMapping("/user/{id}")
    public List<GroupDTO> getGroupsByUserId(@RequestParam Long id) {
        return groupService.findGroupsOfUser(id);
    }

    @PostMapping(path = "/add")
    public Long addGroup(@RequestBody GroupDTO groupDto){
        return groupService.insert(groupDto);
    }

    @DeleteMapping(path = "/remove/{id}")
    public boolean removeGroup(@PathVariable Long id){
        return groupService.delete(id);
    }

    @Override
    public AbstractService getService() {
        return new GroupService();
    }
}