package com.gabor.unit.services;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.database.DatabaseConfiguration;
import com.gabor.partypeps.configurations.database.EntityManagerFactoryConfiguration;
import com.gabor.partypeps.configurations.database.RepositoryConfiguration;
import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.services.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

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
public class UserServiceTest {

    private static Logger logger = Logger.getLogger(UserServiceTest.class.toString());
    private static List<User> mockUsersForTest = new LinkedList<>();

    @Autowired
    public UserService userService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }


    /**
     * Test that checks we do not retrieve the caller user also in the DTOs
     */
    @Test
    public void findAllButNotMeTest() {
        String myUsername = "John";
        List<UserDTO> dtos = userService.findAllButNotMe(myUsername);
        for(UserDTO dto : dtos){
            if(dto.name.equals(myUsername)){
                Assert.fail("Caller user has also been retrieved");
            }
        }
    }


    /**
     * Test that checks the relationships between the users and the available actions between them
     */
    @Test
    public void findUserByUsernameTest() {
        String myUsername = "admin";
        String friend = "John";
        String notMyFriend = "Tricolor";
        String followed = "Dani";
        String follower = "Banani";
        UserDTO myFriend = userService.findUserByUsername(myUsername, friend);
        UserDTO notFriend = userService.findUserByUsername(myUsername, notMyFriend);
        UserDTO iWannaBeHisFriend = userService.findUserByUsername(myUsername, followed);
        UserDTO wantsToBeMyFriend = userService.findUserByUsername(myUsername, follower);
        Assert.assertNotNull("User has not been found", myFriend);
        Assert.assertEquals("Usernames are not equal", friend, myFriend.name);
        Assert.assertTrue("Cannot message a friend", myFriend.canMessage);
        Assert.assertTrue("Cannot view a friends profile", myFriend.canViewProfile);
        Assert.assertFalse("Can still follow a friend", myFriend.canFollow);
        Assert.assertNotNull("User has not been found", notFriend);
        Assert.assertEquals("Usernames are not equal", notMyFriend, notFriend.name);
        Assert.assertFalse("Can message a non-friend", notFriend.canMessage);
        Assert.assertFalse("Can view a non-friends profile", notFriend.canViewProfile);
        Assert.assertTrue("Cannot follow a non-friend", notFriend.canFollow);
        Assert.assertNotNull("User has not been found", iWannaBeHisFriend);
        Assert.assertEquals("Usernames are not equal", followed, iWannaBeHisFriend.name);
        Assert.assertFalse("Can message a followed", iWannaBeHisFriend.canMessage);
        Assert.assertFalse("Can view a followed profile", iWannaBeHisFriend.canViewProfile);
        Assert.assertFalse("Can still follow a followed", iWannaBeHisFriend.canFollow);
        Assert.assertNotNull("User has not been found", wantsToBeMyFriend);
        Assert.assertEquals("Usernames are not equal", follower, wantsToBeMyFriend.name);
        Assert.assertFalse("Can message a follower", wantsToBeMyFriend.canMessage);
        Assert.assertFalse("Can view a follower profile", wantsToBeMyFriend.canViewProfile);
        Assert.assertTrue("Cannot follow a follower", wantsToBeMyFriend.canFollow);
    }


    /**
     * Simple test to check if the user can find himself
     */
    @Test
    public void findMyselfByUsernameTest() {
        String myUsername = "admin";
        UserDTO myself = userService.findMyselfByUsername(myUsername);
        Assert.assertEquals("It did not find the user himself", myself.name, myUsername);
    }


    /**
     * Test to verify that retrieving the user by email gives us the correct user, and no password is added
     */
    @Test
    public void findUserByEmailTest() {
        String email = "bla1";
        UserDTO user = userService.findUserByEmail(email);
        Assert.assertNotNull("Did not find user", user);
        Assert.assertEquals("Did not find the correct user by email", user.email, email);
        Assert.assertFalse("Password was retrieved", user.password != null && !user.password.isEmpty());
    }


    /**
     * Test to check that only friends are retrieved
     */
    @Test
    public void findMyFriendsTest() {
        String myUsername = "admin";
        List<UserDTO> userDtos = userService.findMyFriends(myUsername);
        Assert.assertTrue("To many users have been found", userDtos.size() < 2);
        Assert.assertTrue("To few users have been found", userDtos.size() > 0);
        for(UserDTO dto : userDtos){
            Assert.assertTrue("Non-Friend has been found", ! dto.canFollow && dto.canMessage && dto.canViewProfile);
        }
    }

    @Test
    public void insertAllUsersTest() {
        mockUsersForTest = new LinkedList<>();
        int i = 10;
        while (i > 0) {
            i--;
            User user = new User();
            user.setUsername("Banana" + i);
            user.setEmail("Banana" + i);
            user.setPassword("Banana" + i);
            user.setSubscriptions(new ArrayList<>());
            user.setGroups(new ArrayList<>());
            user.setAuthorities(new ArrayList<>());
            user.setFollowers(new ArrayList<>());
            user.setFollowing(new ArrayList<>());
            mockUsersForTest.add(user);
        }
        for (User user : mockUsersForTest) {
            UserDTO userDto = new UserDTO(user);
            userDto.password = user.getPassword();
            userService.insert(userDto);
        }
    }

    @Test
    public void findAllUsersTest() {
        List<UserDTO> userDTOs = userService.findAll();
        if (userDTOs.size() == 0) {
            Assert.fail();
        }
        for(UserDTO user : userDTOs){
            if(user.password != null && ! user.password.isEmpty()){
                Assert.fail("Password should never be mapped to the DTO");
            }
        }
    }

    @Test
    public void findByUserIdTest() {
        UserDTO user = userService.findById(1L);
        Assert.assertNotNull(user);
    }


    /**
     * Test to check the following of users logic
     */
    @Test
    public void followUserTest() {
        String myUsername = "admin";
        String nonFollowedUser = "Vasile";
        String followedUser = "Dani";
        UserDTO nowFollowedUser = userService.findUserByUsername(myUsername, nonFollowedUser);
        Assert.assertTrue("Cannot follow unfollowed user", nowFollowedUser.canFollow);
        Assert.assertFalse("Can message followed user", nowFollowedUser.canMessage);
        Assert.assertFalse("Can view followed user", nowFollowedUser.canViewProfile);
        Boolean result = userService.followUser(myUsername, nonFollowedUser);
        Assert.assertTrue("Failed to follow", result);
        nowFollowedUser = userService.findUserByUsername(myUsername, nonFollowedUser);
        Assert.assertFalse("Can still follow followed user", nowFollowedUser.canFollow);
        Assert.assertFalse("Can message followed user", nowFollowedUser.canMessage);
        Assert.assertFalse("Can view followed user", nowFollowedUser.canViewProfile);
        UserDTO alreadyFollowedUser = userService.findUserByUsername(myUsername, followedUser);
        Assert.assertFalse("Can still follow followed user", alreadyFollowedUser.canFollow);
        Assert.assertFalse("Can message followed user", alreadyFollowedUser.canMessage);
        Assert.assertFalse("Can view followed user", alreadyFollowedUser.canViewProfile);
        result = userService.followUser(myUsername, followedUser);
        Assert.assertFalse("Succeeded to follow the user again", result);
    }


    /**
     * Test to check the unfollowing of users logic
     */
    @Test
    public void unfollowUserTest() {
        String myUsername = "admin";
        String followedUser = "Vasile";
        String nonFollowedUsername = "Costel";
        UserDTO alreadyFollowedUser = userService.findUserByUsername(myUsername, followedUser);
        Assert.assertFalse("Can still follow followed user", alreadyFollowedUser.canFollow);
        Assert.assertFalse("Can message followed user", alreadyFollowedUser.canMessage);
        Assert.assertFalse("Can view followed user", alreadyFollowedUser.canViewProfile);
        Boolean result = userService.unfollowUser(myUsername, followedUser);
        Assert.assertTrue("Failed to unfollow a followed user", result);
        alreadyFollowedUser = userService.findUserByUsername(myUsername, followedUser);
        Assert.assertTrue("Cannot follow unfollowed user", alreadyFollowedUser.canFollow);
        Assert.assertFalse("Can message unfollowed user", alreadyFollowedUser.canMessage);
        Assert.assertFalse("Can view unfollowed user", alreadyFollowedUser.canViewProfile);
        UserDTO nonFollowedUser = userService.findUserByUsername(myUsername, nonFollowedUsername);
        Assert.assertTrue("Cannot follow unfollowed user", nonFollowedUser.canFollow);
        Assert.assertFalse("Can message followed user", nonFollowedUser.canMessage);
        Assert.assertFalse("Can view followed user", nonFollowedUser.canViewProfile);
        result = userService.unfollowUser(myUsername, nonFollowedUsername);
        Assert.assertTrue("Succeeded to unfollow already unfollowed user", result);
    }


    /**
     * Test to check that only admin users cand delete other users
     */
    @Test
    public void removeUserTest() {
        String myUsername = "John";
        Long toDeleteUserId = 7L;
        Boolean result = userService.removeUser(myUsername, toDeleteUserId);
        Assert.assertFalse("Non admin user could delete another user", result);
        myUsername = "admin";
        result = userService.removeUser(myUsername, toDeleteUserId);
        Assert.assertTrue("Admin user could not delete another user", result);
    }

    @Test
    public void getRepositoryTest() {
        JpaRepository<User, Long> repository = userService.getRepository();
        Assert.assertNotNull("The mapper returned by the service is null", repository);
    }

    @Test
    public void getMapperTest() {
        AbstractMapper mapper = userService.getMapper();
        Assert.assertNotNull("The mapper returned by the service is null", mapper);
    }
}