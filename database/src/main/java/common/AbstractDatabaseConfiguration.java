package common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public interface AbstractDatabaseConfiguration {

    String H2_DRIVER = "org.h2.Driver";
    String MySQL_DRIVER = "org.mysql.Driver";
    String POSTGRES_DRIVER = "org.postgresql.Driver";

    @Bean("dataSource")
    @Profile("DEV")
    DataSource getDataSourceForDEV();

    @Bean("dataSource")
    @Profile("IT")
    DataSource getDataSourceForIT();

    @Bean("dataSource")
    @Profile("PROD")
    DataSource getDataSourceForPROD();

}
