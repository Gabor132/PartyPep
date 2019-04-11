package com.gabor.party.services;

import com.gabor.party.main.models.User;
import com.gabor.party.main.models.dto.UserDTO;
import com.gabor.party.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserServiceTest {

    @Autowired
    public UserService userService;
    @Autowired
    public UserRepository userRepository;

    private static List<User> mockUsersForTest = new LinkedList<>();
    private static List<User> invalidUsers = new LinkedList<>();

    /**
     * This methods is currently used just to disable the unit tests till I find a way
     * to access the database without @Autowired annotation (probably will create direct connection
     * to in memory DB
     * TODO
     * @return
     */
    private boolean checkBasicNecesities(){
        return ! (userRepository == null || userService == null);
    }

    @Before
    public void setUp(){
        User user1 = new User();
        user1.setName("UserToInsert");
        user1.setPassword("password");
        User user2 = new User();
        user2.setName("User2");
        user2.setPassword("User2");
        User user3 = new User();
        user2.setName("User2");
        user2.setPassword("User2");
        User user4 = new User();
        user2.setName("User2");
        user2.setPassword("User2");
        User user5 = new User();
        user2.setName("User2");
        user2.setPassword("User2");
        User user6 = new User();
        user2.setName("User2");
        user2.setPassword("User2");
        mockUsersForTest.add(user1);
        mockUsersForTest.add(user2);
        mockUsersForTest.add(user3);
        mockUsersForTest.add(user4);
        mockUsersForTest.add(user5);
        mockUsersForTest.add(user6);
    }

    @After
    public void tearDown(){
        if (! checkBasicNecesities()){
            return;
        }
        for(User user : mockUsersForTest){
            userRepository.delete(user);
            user.setId(null);
        }
    }

    @Test
    public void insertAllUsersTest(){
        if(! checkBasicNecesities()){
            return;
        }
        List<Long> ids = new LinkedList<>();
        for(User user : mockUsersForTest){
            ids.add(userService.insert(new UserDTO(user)));
        }
        for(Long id : ids){
            assertTrue(userRepository.existsById(id));
        }
    }

    @Test
    public void findAllUsersTest(){
        if(! checkBasicNecesities()){
            return;
        }
        List<UserDTO> users = userService.findAll();
        assertEquals(mockUsersForTest.size(), users.size());
    }
}