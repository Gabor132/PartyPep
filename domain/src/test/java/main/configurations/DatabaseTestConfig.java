package main.configurations;

import common.DatabaseConfiguration;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class DatabaseTestConfig extends DatabaseConfiguration {
    private final static Logger logger = Logger.getLogger(DatabaseTestConfig.class.toString());
}
