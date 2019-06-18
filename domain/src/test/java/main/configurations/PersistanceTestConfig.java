package main.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Configuration
public class PersistanceTestConfig {

    @Bean("persistanceUnit")
    @PersistenceContext(name = "persistance-unit", type = PersistenceContextType.TRANSACTION)
    public Persistence persistanceUnit(){
        Persistence pers = new Persistence();
        return pers;
    }

}
