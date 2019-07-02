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
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setUsername(properties.getProperty("postgres.user"));
        dataSource.setPassword(properties.getProperty("postgres.password"));
        dataSource.setUrl(properties.getProperty("postgres.url"));
        return dataSource;
    }

    @Bean("dataSource")
    @Profile("IT")
    @Override
    public DataSource getDataSourceForIT() {
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, "IT");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setUsername(properties.getProperty("postgres.user"));
        dataSource.setPassword(properties.getProperty("postgres.password"));
        dataSource.setUrl(properties.getProperty("postgres.url"));
        return dataSource;
    }

    @Bean("dataSource")
    @Profile("PROD")
    @Override
    public DataSource getDataSourceForPROD() {
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, "PROD");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        String env = System.getenv(properties.getProperty("postgres.url"));
        dataSource.setUsername(System.getenv(properties.getProperty("postgres.user")));
        dataSource.setPassword(System.getenv(properties.getProperty("postgres.password")));
        dataSource.setUrl(System.getenv(properties.getProperty("postgres.url")));
        return dataSource;
    }
}
