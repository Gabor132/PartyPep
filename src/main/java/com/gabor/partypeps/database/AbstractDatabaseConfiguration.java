package com.gabor.partypeps.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public interface AbstractDatabaseConfiguration {

    String FROM_ENV_KEY = "isFromEnv";
    String DRIVER_KEY = "driver";
    String USERNAME_KEY = "user";
    String PASSWORD_KEY = "password";
    String URL_KEY = "url";


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
        boolean isFromENV = Boolean.parseBoolean(properties.getProperty(FROM_ENV_KEY));
        dataSource.setDriverClassName(getProperty(DRIVER_KEY, properties, isFromENV));
        dataSource.setUsername(getProperty(USERNAME_KEY, properties, isFromENV));
        dataSource.setPassword(getProperty(PASSWORD_KEY, properties, isFromENV));
        dataSource.setUrl(getProperty(URL_KEY, properties, isFromENV));

    }

    static String getProperty(String key, Properties properties, boolean isFromEnv) {
        return isFromEnv ? System.getenv(properties.getProperty(key)) : properties.getProperty(key);
    }
}
