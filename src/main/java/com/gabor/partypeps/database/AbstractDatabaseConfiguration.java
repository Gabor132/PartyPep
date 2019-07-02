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
        dataSource.setDriverClassName(getProperty(driverKey, properties, isFromENV));
        dataSource.setUsername(getProperty(usernameKey, properties, isFromENV));
        dataSource.setPassword(getProperty(passwordKey, properties, isFromENV));
        dataSource.setUrl(getProperty(urlKey, properties, isFromENV));
    }

    static String getProperty(String key, Properties properties, boolean isFromEnv) {
        return isFromEnv ? System.getenv(properties.getProperty(key)) : properties.getProperty(key);
    }
}
