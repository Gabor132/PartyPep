package com.gabor.partypeps.database;

import com.gabor.common.AbstractTest;
import com.gabor.partypeps.configurations.DatabaseConfig;
import com.gabor.partypeps.enums.ProfilesEnum;
import org.junit.After;
import org.junit.Before;
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
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, ProfilesEnum.PROD);
        //
        // Replace the isFromEnv value to false so that we don't check in the Environment Variables
        // The fact that we find all the necessary properties as being defined for PROD should be enough for now
        //
        properties.setProperty(AbstractDatabaseConfiguration.fromENVKey, "false");
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
    }

    @Test
    public void getProperty() {
    }
}