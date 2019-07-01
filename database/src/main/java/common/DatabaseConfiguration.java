package common;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DatabaseConfiguration implements AbstractDatabaseConfiguration{


    @Override
    public DataSource getDataSourceForDEV() {

        System.out.println("GETTING DEFAULT DEV DATASOURCE");
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, "DEV");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(H2_DRIVER);
        dataSource.setUsername(properties.getProperty("h2.user"));
        dataSource.setPassword(properties.getProperty("h2.password"));
        dataSource.setUrl(properties.getProperty("h2.url"));
        return dataSource;
    }

    @Override
    public DataSource getDataSourceForIT() {
        System.out.println("GETTING DEFAULT IT DATASOURCE");
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, "IT");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setUsername(properties.getProperty("postgres.user"));
        dataSource.setPassword(properties.getProperty("postgres.password"));
        dataSource.setUrl(properties.getProperty("postgres.url"));
        return dataSource;
    }

    @Override
    public DataSource getDataSourceForPROD() {
        System.out.println("GETTING DEFAULT PROD DATASOURCE");
        Properties properties = DatabasePropertiesHelper.getJDBCProperties(true, "PROD");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setUsername(properties.getProperty("postgres.user"));
        dataSource.setPassword(properties.getProperty("postgres.password"));
        dataSource.setUrl(properties.getProperty("postgres.url"));
        return dataSource;
    }
}
