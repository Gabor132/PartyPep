package com.gabor.partypeps.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public interface AbstractDatabaseConfiguration {

    String fromENVKey = "isFromEnv";
    String driverKey = "driver";
    String usernameKey = "user";
    String passwordKey = "password";
    String urlKey = "url";


    @Bean("dataSource")
    @Profile("DEV")
    DataSource getDataSourceForDEV();

    @Bean("dataSource")
    @Profile("IT")
    DataSource getDataSourceForIT();

    @Bean("dataSource")
    @Profile("PROD")
    DataSource getDataSourceForPROD();

    static void setupDataSource(DriverManagerDataSource dataSource, Properties properties) {
        boolean isFromENV = Boolean.parseBoolean(properties.getProperty(fromENVKey));
        String driver = properties.getProperty(driverKey);
        String username = properties.getProperty(usernameKey);
        String password = properties.getProperty(passwordKey);
        String url = properties.getProperty(urlKey);
        if (isFromENV) {
            dataSource.setDriverClassName(System.getenv(driver));
            dataSource.setUsername(System.getenv(username));
            dataSource.setPassword(System.getenv(password));
            dataSource.setUrl(System.getenv(url));
        } else {
            dataSource.setDriverClassName(driver);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setUrl(url);
        }
    }
}
