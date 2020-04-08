package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dto.GroupDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController(value = "groups")
@RequestMapping(path = "groups")
public class GroupController extends AbstractController<Group> {

    @Autowired
    public GroupService groupService;

    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GroupDTO> getAllGroups() {
        return groupService.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GroupDTO getGroupById(@PathVariable Long id) {
        return groupService.findById(id);
    }


    @GetMapping(path = "/group/{groupname}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GroupDTO getGroupByName(@PathVariable String groupname) {
        return groupService.findGroupByName(groupname);
    }

    @GetMapping(path = "/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GroupDTO> getGroupsByUserId(@PathVariable Long id) {
        return groupService.findGroupsOfAUser(id);
    }

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long addGroup(@RequestBody GroupDTO groupDto) {
        return groupService.insert(groupDto);
    }

    @PutMapping(path="/invite/user/{groupId}/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long addInvitationToGroup(Principal principal, @PathVariable Long groupId, @PathVariable String username){
        return groupService.addUserToGroup(principal.getName(), groupId, username);
    }

    @DeleteMapping(path = "/remove/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean removeGroup(@PathVariable Long id) {
        return groupService.delete(id);
    }

    @Override
    public AbstractService getService() {
        return new GroupService();
    }
}
