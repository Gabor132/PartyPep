package com.gabor.partypeps.configurations.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class EntityManagerFactoryConfiguration {

    @Autowired
    DataSource dataSource;

    @Bean("entityManagerFactory")
    @Profile("DEV")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDEV() {
        LocalContainerEntityManagerFactoryBean entityManager
                = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.gabor.partypeps.models.dao");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaProperties(additionalPropertiesDEV());
        entityManager.setPersistenceUnitName("dev-persistance-unit");
        return entityManager;
    }

    private Properties additionalPropertiesDEV() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "false");
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        //
        // Set DB Drop, Create and Data scripts
        //
        properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
        properties.setProperty("javax.persistence.schema-generation.create-source", "script");
        properties.setProperty("javax.persistence.schema-generation.create-script-source", "META-INF/sql_scripts/0.0.1/create.sql");
        properties.setProperty("javax.persistence.schema-generation.drop-source", "script");
        properties.setProperty("javax.persistence.schema-generation.drop-script-source", "META-INF/sql_scripts/0.0.1/drop.sql");
        properties.setProperty("javax.persistence.sql-load-script-source", "META-INF/sql_scripts/0.0.1/data.sql");

        return properties;
    }

    @Bean("entityManagerFactory")
    @Profile("IT")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryIT() {
        LocalContainerEntityManagerFactoryBean entityManager
                = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.gabor.partypeps.models.dao");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaProperties(additionalPropertiesIT());
        entityManager.setPersistenceUnitName("it-persistance-unit");
        return entityManager;
    }

    private Properties additionalPropertiesIT() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "false");
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        //
        // Set DB Drop, Create and Data scripts
        //
        properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
        properties.setProperty("javax.persistence.schema-generation.create-source", "script");
        properties.setProperty("javax.persistence.schema-generation.create-script-source", "META-INF/sql_scripts/0.0.1/create.sql");
        properties.setProperty("javax.persistence.schema-generation.drop-source", "script");
        properties.setProperty("javax.persistence.schema-generation.drop-script-source", "META-INF/sql_scripts/0.0.1/drop.sql");
        properties.setProperty("javax.persistence.sql-load-script-source", "META-INF/sql_scripts/0.0.1/data.sql");
        return properties;
    }

    @Bean("entityManagerFactory")
    @Profile("PROD | default")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPROD() {
        LocalContainerEntityManagerFactoryBean entityManager
                = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.gabor.partypeps.models.dao");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setPersistenceUnitName("prod-persistance-unit");
        entityManager.setJpaProperties(additionalPropertiesPROD());
        return entityManager;
    }

    private Properties additionalPropertiesPROD() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.use_sql_comments", "false");
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        //
        // Set DB Drop, Create and Data scripts
        //
        properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
        properties.setProperty("javax.persistence.schema-generation.create-source", "script");
        properties.setProperty("javax.persistence.schema-generation.create-script-source", "sql_scripts/0.0.1/create.sql");
        properties.setProperty("javax.persistence.schema-generation.drop-source", "script");
        properties.setProperty("javax.persistence.schema-generation.drop-script-source", "sql_scripts/0.0.1/drop.sql");
        properties.setProperty("javax.persistence.sql-load-script-source", "sql_scripts/0.0.1/data.sql");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
