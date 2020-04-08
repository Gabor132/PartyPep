package com.gabor.unit.services;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.database.DatabaseConfiguration;
import com.gabor.partypeps.configurations.database.EntityManagerFactoryConfiguration;
import com.gabor.partypeps.configurations.database.RepositoryConfiguration;
import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.Group;
import com.gabor.partypeps.models.dto.GroupDTO;
import com.gabor.partypeps.services.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest(classes = {
        DatabaseConfiguration.class,
        EntityManagerFactoryConfiguration.class,
        RepositoryConfiguration.class,
        MapperTestConfiguration.class,
        ServiceTestConfiguration.class,
        UrlTestConfiguration.class
})
@ActiveProfiles(value = "DEV")
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupServiceTest {

    @Autowired
    public GroupService groupService;

    /**
     * Test to check the insertion of new users
     */
    @Test
    public void insertTest() {
        GroupDTO group = new GroupDTO();
        group.name = "Viva la Vida";
        List<String> usernames = new LinkedList<>();
        group.usersUsernames = usernames;
        group.usersUsernames.add("Vasile");
        group.usersUsernames.add("Tricolor");
        long id = groupService.insert(group);
        Assert.isTrue(id > 0L, "Group was not created");
        group = groupService.findById(id);
        Assert.isTrue(group.name.equals("Viva la Vida"), "Created group is inconsistent with what was inserted");
        Assert.isTrue(group.usersUsernames.stream().filter(usernames::contains).count() == 2, "Not all desired users were added on the group!");
    }

    /**
     * Test to check the adding of a user into a group
     */
    @Test
    public void addUserToGroupTest(){
        String myUsername = "Dani";
        String hisUsername = "Tricolor";
        Long groupId = 2L;
        Long idOfGroup = groupService.addUserToGroup(myUsername, groupId, hisUsername);
        Assert.isTrue(groupId.equals(idOfGroup), "The adding of the new user to the group did not work");
        hisUsername = "Herr Banana";
        idOfGroup = groupService.addUserToGroup(myUsername, groupId, hisUsername);
        Assert.isTrue(idOfGroup.equals(0L), "The adding of the non existent user to the group worked");
        groupId = 76L;
        hisUsername = "Vasile";
        idOfGroup = groupService.addUserToGroup(myUsername, groupId, hisUsername);
        Assert.isTrue(idOfGroup.equals(0L), "The adding of the new user to the non existing group worked");
        myUsername = "Babuta";
        groupId = 2L;
        hisUsername = "Pascalake";
        idOfGroup = groupService.addUserToGroup(myUsername, groupId, hisUsername);
        Assert.isTrue(idOfGroup.equals(0L), "The adding of the new user to the existing group worked by a non member worked");
    }

    /**
     * Test to check the finding of a user's groups
     */
    @Test
    public void findGroupsOfUserTest() {
        long myId = 1L;
        List<String> groupNames = new ArrayList<>();
        groupNames.add("Nu Plange Ana!");
        groupNames.add("The fantastic 4rs");
        List<GroupDTO> groups = groupService.findGroupsOfAUser(myId);
        Assert.isTrue(groups.size() == 2, "User should've been only in two groups");
        Assert.isTrue(groups.stream().map(g -> g.name).filter(groupNames::contains).count() == 2, "User does not belong to the correct groups");
    }

    /**
     * Test that finding a group by name works
     */
    @Test
    public void findGroupByNameTest(){
        String groupName = "Nu Plange Ana!";
        String wrongName = "Plange te rog frumos, Ana!";
        GroupDTO group = groupService.findGroupByName(groupName);
        Assert.isTrue(group != null && group.name.equals(groupName), "Did not find the group by name");
        group = groupService.findGroupByName(wrongName);
        Assert.isTrue(group == null, "Did find a non existent group");
    }

    @Test
    public void getRepositoryTest() {
        JpaRepository<Group, Long> repository = groupService.getRepository();
        Assert.notNull(repository, "The mapper returned by the service is null");
    }

    @Test
    public void getMapperTest() {
        AbstractMapper mapper = groupService.getMapper();
        Assert.notNull(mapper, "The mapper returned by the service is null");
    }
}