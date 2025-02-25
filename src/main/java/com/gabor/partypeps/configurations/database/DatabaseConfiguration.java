package com.gabor.partypeps.configurations.database;

import com.gabor.partypeps.common.props.PropertiesHelper;
import com.gabor.partypeps.database.AbstractDatabaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

import static com.gabor.partypeps.enums.ProfilesEnum.*;

@Configuration
public class DatabaseConfiguration implements AbstractDatabaseConfiguration {

    @Bean("dataSource")
    @Profile("DEV")
    @Override
    public DataSource getDataSourceForDEV() {
        Properties properties = PropertiesHelper.getJDBCProperties(true, DEV);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
        return dataSource;
    }

    @Bean("dataSource")
    @Profile("IT")
    @Override
    public DataSource getDataSourceForIT() {
        Properties properties = PropertiesHelper.getJDBCProperties(true, IT);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
        return dataSource;
    }

    @Bean("dataSource")
    @Profile("PROD | default")
    @Override
    public DataSource getDataSourceForPROD() {
        Properties properties = PropertiesHelper.getJDBCProperties(true, PROD);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        AbstractDatabaseConfiguration.setupDataSource(dataSource, properties);
        return dataSource;
    }

}
