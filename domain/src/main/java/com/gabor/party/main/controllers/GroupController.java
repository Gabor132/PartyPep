package com.gabor.party.main.controllers;

import com.gabor.party.main.models.dao.Group;
import com.gabor.party.main.models.dto.GroupDTO;
import com.gabor.party.services.GroupService;
import common.controller.AbstractController;
import common.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController extends AbstractController<Group> {

    @Autowired
    public GroupService groupService;

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public List<GroupDTO> getAllGroups() {
        return groupService.findAll();
    }

    @RequestMapping("/groups/{id}")
    public GroupDTO getGroupById(@RequestParam Long id) {
        return (GroupDTO) groupService.findById(id);
    }

    @RequestMapping("/groups/user/{id}")
    public List<GroupDTO> getGroupsByUserId(@RequestParam Long id) {
        return groupService.findGroupsOfUser(id);
    }

    @PostMapping(path = "/groups/add")
    public Long addGroup(@RequestBody GroupDTO groupDto){
        return groupService.insert(groupDto);
    }

    @DeleteMapping(path = "/groups/remove/{id}")
    public boolean removeGroup(@PathVariable Long id){
        return groupService.delete(id);
    }

    @Override
    public AbstractService getService() {
        return new GroupService();
    }
}
