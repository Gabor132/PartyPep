package com.gabor.party.database;


import com.gabor.auxiliars.SQLReader;
import com.gabor.party.configurations.RepositoryConfiguration;
import com.gabor.party.main.models.dao.User;
import com.gabor.party.repositories.UserRepository;
import main.configurations.*;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={
        DatabaseTestConfig.class,
        EntityManagerFactoryTestConfig.class,
        RepositoryConfiguration.class,
        PartyPepsTestConfiguration.class
})
public class DatabaseTest{

    @Resource
    public UserRepository userRepository;

    @Autowired
    public DataSource dataSource;

    private final static Logger logger = Logger.getLogger(DatabaseTest.class.toString());

    @Test
    public void testConnection(){
        try(Connection conn = dataSource.getConnection()){
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery("SELECT 1")) {
                if(resultSet.next() && resultSet.getInt(1) != 1) {
                    Assert.fail();
                }
            }
        }catch(SQLException exception){
            Assert.fail("Failed to connect to database: " + exception.getMessage());
        }
    }

    @Test
    public void databaseBasicInsert(){
        User user = new User();
        user.setName("UserToInsert");
        user.setPassword("password");
        user.setGroups(new ArrayList<>());
        user.setInvitations(new ArrayList<>());
        user = userRepository.save(user);
        Assert.assertNotNull(user.getId());
        Assert.assertNotEquals(user.getId(),0.0);
    }

    @Test
    public void databaseBasicSelectAll(){
        List<User> users = userRepository.findAll();
        Assert.assertTrue(users.size() > 0);
    }
}
