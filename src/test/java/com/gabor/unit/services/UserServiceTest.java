package com.gabor.unit.services;

import com.gabor.configurations.MapperTestConfiguration;
import com.gabor.configurations.ServiceTestConfiguration;
import com.gabor.configurations.UrlTestConfiguration;
import com.gabor.partypeps.configurations.database.DatabaseConfiguration;
import com.gabor.partypeps.configurations.database.EntityManagerFactoryConfiguration;
import com.gabor.partypeps.configurations.database.RepositoryConfiguration;
import com.gabor.partypeps.mappers.AbstractMapper;
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
     * TODO
     */
    @Test
    public void findAllButNotMeTest() {
    }


    /**
     * TODO
     */
    @Test
    public void findUserByUsernameTest() {
    }


    /**
     * TODO
     */
    @Test
    public void findMyselfByUsernameTest() {
    }


    /**
     * TODO
     */
    @Test
    public void findUserByEmailTest() {
    }


    /**
     * TODO
     */
    @Test
    public void findMyFriendsTest() {
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


    /**
     * TODO
     */
    @Test
    public void followUserTest() {
    }


    /**
     * TODO
     */
    @Test
    public void unfollowUserTest() {
    }


    /**
     * TODO
     */
    @Test
    public void suicideTest() {
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