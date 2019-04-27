package com.gabor.party.main.controllers;

import com.gabor.party.main.models.dto.GroupDTO;
import com.gabor.party.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {

    @Autowired
    public GroupService groupService;

    @RequestMapping("/groups/all")
    public List<GroupDTO> getAllGroups(){ return groupService.findAll(); }

    @RequestMapping("/groups/{id}")
    public GroupDTO getGroupById(@RequestParam Long id){
        return (GroupDTO) groupService.findById(id);
    }

    @RequestMapping("/groups/user/{id}")
    public List<GroupDTO> getGroupsByUserId(@RequestParam Long id){
        return groupService.findGroupsOfUser(id);
    }
}
