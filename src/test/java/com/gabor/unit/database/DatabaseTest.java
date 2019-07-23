package com.gabor.unit.database;


import com.gabor.common.AbstractTest;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.configurations.EntityManagerFactoryConfig;
import com.gabor.partypeps.configurations.RepositoryConfiguration;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringBootTest(classes = {
        DatabaseConfig.class,
        EntityManagerFactoryConfig.class,
        RepositoryConfiguration.class
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "DEV")
public class DatabaseTest extends AbstractTest {

    private final static Logger logger = Logger.getLogger(DatabaseTest.class.toString());

    @Resource
    public UserRepository userRepository;
    @Autowired
    public DataSource dataSource;

    @Test
    public void testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery("SELECT 1")) {
                if (!resultSet.next()) {
                    Assert.fail();
                } else if (resultSet.getInt(1) != 1) {
                    Assert.fail();
                }
            }
        } catch (SQLException exception) {
            Assert.fail("Failed to connect to database: " + exception.getMessage());
        }
    }

    @Test
    public void databaseBasicInsert() {
        User user = new User();
        user.setName("UserToInsert");
        user.setPassword("password");
        user.setGroups(new ArrayList<>());
        user.setInvitations(new ArrayList<>());
        user = userRepository.save(user);
        Assert.assertNotEquals(user.getId(), 0.0);
    }

    @Test
    public void databaseBasicSelectAll() {
        List<User> users = userRepository.findAll();
        Assert.assertTrue(users.size() > 0);
    }

}
