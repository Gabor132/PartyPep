package com.gabor.party.services;

import com.gabor.partypeps.models.dto.AbstractDTO;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.services.AbstractService;
import main.configurations.PartyPepsTestConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        DatabaseConfig.class,
        EntityManagerFactoryConfig.class,
        RepositoryConfiguration.class,
        PartyPepsTestConfiguration.class
})
@ContextConfiguration(classes = PartyPepsTestConfiguration.class)
public class UserServiceTest  implements WebApplicationInitializer {

    private static Logger logger = Logger.getLogger(UserServiceTest.class.toString());
    private static List<User> mockUsersForTest = new LinkedList<>();
    @Autowired
    public AbstractService userService;

    @Before
    public void setUp() {
        int i = 10;
        while (i > 0) {
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

    @Test
    public void deleteUserTest() {
    }

    @Test
    public void updateUserTest() {
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.active", "IT");
    }
}