package main.configurations;

import com.gabor.party.configurations.RepositoryConfiguration;
import com.gabor.party.services.MessageService;
import com.gabor.party.services.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootTest(classes = {
        RepositoryConfiguration.class,
        DatabaseTestConfig.class,
        EntityManagerFactoryTestConfig.class,
        TestWebConfiguration.class
})
public class PartyPepsTestConfiguration {

    @Bean("userService")
    public UserService userService() {
        return new UserService();
    }

    @Bean("messageService")
    public MessageService messageService(){
        return new MessageService();
    }

}
