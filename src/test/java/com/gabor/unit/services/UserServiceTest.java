package com.gabor.unit.services;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.DatabaseConfiguration;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfiguration;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.AbstractDTO;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.services.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        int i = 10;
        while (i > 0) {
            i--;
            User user = new User();
            user.setUsername("Banana" + i);
            user.setPassword("Banana" + i);
            user.setInvitations(new ArrayList<>());
            user.setGroups(new ArrayList<>());
            user.setAuthorities(new ArrayList<>());
            mockUsersForTest.add(user);
        }
    }

    @After
    public void tearDown() {

    }

    @Test
    public void insertAllUsersTest() {
        for (User user : mockUsersForTest) {
            UserDTO userDto = new UserDTO(user);
            userService.insert(userDto);
        }
    }

    @Test
    public void findAllUsersTest() {
        List<? extends AbstractDTO> userDTOs = userService.findAll();
        if (userDTOs.size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void findByUserIdTest() {
        UserDTO user = (UserDTO) userService.findById(1L);
        Assert.assertNotNull(user);
    }

    /**
     * TODO
     */
    @Test
    public void deleteUserTest() {
    }

    /**
     * TODO
     */
    @Test
    public void updateUserTest() {
    }
}