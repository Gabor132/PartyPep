package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dto.GroupDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "groups")
@RequestMapping(path = "groups")
public class GroupController extends AbstractController<Group> {

    @Autowired
    public GroupService groupService;

    @GetMapping(path = "/all")
    public List<GroupDTO> getAllGroups() {
        return groupService.findAll();
    }

    @GetMapping(path = "/{id}")
    public GroupDTO getGroupById(@PathVariable Long id) {
        return groupService.findById(id);
    }

    @GetMapping(path = "/user/{id}")
    public List<GroupDTO> getGroupsByUserId(@PathVariable Long id) {
        return groupService.findGroupsOfUser(id);
    }

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addGroup(@RequestBody GroupDTO groupDto) {
        return groupService.insert(groupDto);
    }

    @DeleteMapping(path = "/remove/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean removeGroup(@PathVariable Long id) {
        return groupService.delete(id);
    }

    @Override
    public AbstractService getService() {
        return new GroupService();
    }
}
