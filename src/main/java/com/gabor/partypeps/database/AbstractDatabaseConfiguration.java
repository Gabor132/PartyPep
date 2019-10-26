package com.gabor.partypeps.database;

import com.gabor.partypeps.common.props.PropertiesHelper;
import com.gabor.partypeps.enums.PropertiesEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public interface AbstractDatabaseConfiguration {

    @Bean("dataSource")
    @Profile("DEV")
    DataSource getDataSourceForDEV();

    @Bean("dataSource")
    @Profile("IT")
    DataSource getDataSourceForIT();

    @Bean("dataSource")
    @Profile("PROD | default")
    DataSource getDataSourceForPROD();

    static void setupDataSource(DriverManagerDataSource dataSource, Properties properties) {
        dataSource.setDriverClassName(PropertiesHelper.getProperty(properties, PropertiesEnum.JDBC_DRIVER));
        dataSource.setUsername(PropertiesHelper.getProperty(properties, PropertiesEnum.JDBC_USER));
        dataSource.setPassword(PropertiesHelper.getProperty(properties, PropertiesEnum.JDBC_PASSWORD));
        dataSource.setUrl(PropertiesHelper.getProperty(properties, PropertiesEnum.JDBC_URL));

    }
}
