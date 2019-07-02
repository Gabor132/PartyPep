package com.gabor.partypeps.configurations;

import com.gabor.partypeps.database.AbstractDatabaseConfiguration;
import com.gabor.partypeps.database.DatabasePropertiesHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DatabaseConfig implements AbstractDatabaseConfiguration {

    @Bean("dataSource")
    @Profile("DEV")
    @Override
    public DataSource getDataSourceForDEV() {
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, "DEV");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
        return dataSource;
    }

    @Bean("dataSource")
    @Profile("IT")
    @Override
    public DataSource getDataSourceForIT() {
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, "IT");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
        return dataSource;
    }

    @Bean("dataSource")
    @Profile("PROD")
    @Override
    public DataSource getDataSourceForPROD() {
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, "PROD");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
        return dataSource;
    }

}
