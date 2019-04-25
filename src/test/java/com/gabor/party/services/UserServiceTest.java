package com.gabor.party.services;

import com.gabor.party.configurations.RepositoryConfiguration;
import com.gabor.party.main.models.dao.User;
import com.gabor.party.main.models.dto.AbstractDTO;
import com.gabor.party.main.models.dto.UserDTO;
import com.gabor.party.repositories.UserRepository;
import main.configurations.DatabaseTestConfig;
import main.configurations.EntityManagerFactoryTestConfig;
import main.configurations.PartyPepsTestConfiguration;
import main.configurations.RepositoryTestConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={
        DatabaseTestConfig.class,
        EntityManagerFactoryTestConfig.class,
        RepositoryConfiguration.class,
        PartyPepsTestConfiguration.class
})
@ContextConfiguration(classes = PartyPepsTestConfiguration.class)
public class UserServiceTest {

    @Autowired
    public AbstractService userService;

    private static Logger logger = Logger.getLogger(UserServiceTest.class.toString());

    private static List<User> mockUsersForTest = new LinkedList<>();

    @Before
    public void setUp(){
        int i = 10;
        while(i > 0) {
            i--;
            User user = new User();
            user.setName("Banana" + i);
            user.setPassword("Banana" + i);
            user.setInvitations(new ArrayList<>());
            user.setGroups(new ArrayList<>());
            mockUsersForTest.add(user);
        }
    }

    @After
    public void tearDown(){

    }

    @Test
    public void insertAllUsersTest(){
        for(User user : mockUsersForTest){
            UserDTO userDto = new UserDTO(user);
            userService.insert(userDto);
        }
    }

    @Test
    public void findAllUsersTest(){
        List<UserDTO> users = (List<UserDTO>) userService.findAll();
        if (users.size() == 0){
            Assert.fail();
        }
    }

    @Test
    public void findByUserIdTest() {
        UserDTO user = (UserDTO) userService.findById(1L);
        Assert.assertNotNull(user);
    }

    @Test
    public void deleteUserTest() {
    }

    @Test
    public void updateUserTest() {
    }
}