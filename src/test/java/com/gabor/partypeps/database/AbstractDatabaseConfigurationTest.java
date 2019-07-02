package com.gabor.partypeps.database;

import com.gabor.common.AbstractTest;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.enums.ProfilesEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AbstractDatabaseConfigurationTest extends AbstractTest {

    @Test
    public void setupDataSourceDEV() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, ProfilesEnum.DEV);
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
    }


    @Test
    public void setupDataSourceIT() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, ProfilesEnum.IT);
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
    }


    @Test
    public void setupDataSourcePROD() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, ProfilesEnum.PROD);
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
    }

    @Test
    public void getProperty() {
    }
}