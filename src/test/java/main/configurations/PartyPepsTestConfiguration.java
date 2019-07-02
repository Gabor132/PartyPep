package main.configurations;

import com.gabor.party.services.MessageService;
import com.gabor.party.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
