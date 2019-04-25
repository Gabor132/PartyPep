package main.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class DatabaseTestConfig {


    private final static Logger logger = Logger.getLogger(DatabaseTestConfig.class.toString());

    @Bean("dataSource")
    public DataSource dataSourceTest(){
        logger.log(Level.INFO, "RETRIEVING DATASOURCE FOR TESTS");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("admin");
        dataSource.setUrl("jdbc:postgresql://localhost:5433/");
        return dataSource;
    }

}
