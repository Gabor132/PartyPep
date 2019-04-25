package com.gabor.party.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean("dataSource")
    @Profile("DEV")
    public DataSource dataSourceDEV(){
        System.out.println("GETTING DEV DATASOURCE");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("admin");
        dataSource.setUrl("jdbc:postgresql://localhost:5433/");
        return dataSource;
    }

    @Bean("dataSource")
    @Profile("IT")
    public DataSource dataSourceIT(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(System.getenv("JDBC_DATABASE_URL"));
        return dataSource;
    }

    @Bean("dataSource")
    @Profile("PROD")
    public DataSource dataSourcePROD(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:h2:mem:test");
        return dataSource;
    }

}
