package com.gabor.partypeps.database;

import com.gabor.common.AbstractTest;
import com.gabor.partypeps.common.PropertiesHelper;
import com.gabor.partypeps.enums.ProfilesEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "DEV,IT,PROD")
public class AbstractDatabaseConfigurationTest extends AbstractTest {

    @Test
    public void setupDataSourceDEV() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = PropertiesHelper.getJDBCProperties(true, ProfilesEnum.DEV);
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
    }


    @Test
    public void setupDataSourceIT() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = PropertiesHelper.getJDBCProperties(true, ProfilesEnum.IT);
        //
        // Replace the isFromEnv value to false so that we don't check in the Environment Variables
        // The fact that we find all the necessary properties as being defined for IT should be enough for now
        //
        properties.setProperty(AbstractDatabaseConfiguration.fromENVKey, "false");
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
    }


    @Test
    public void setupDataSourcePROD() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = PropertiesHelper.getJDBCProperties(true, ProfilesEnum.PROD);
        //
        // Replace the isFromEnv value to false so that we don't check in the Environment Variables
        // The fact that we find all the necessary properties as being defined for PROD should be enough for now
        //
        properties.setProperty(AbstractDatabaseConfiguration.fromENVKey, "false");
        properties.setProperty(AbstractDatabaseConfiguration.driverKey, "org.postgresql.Driver");
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
    }

    @Test
    public void getProperty() {
    }
}