package main.configurations;

import com.gabor.partypeps.mappers.MessageMapper;
import com.gabor.partypeps.mappers.UserMapper;
import com.gabor.partypeps.services.MessageService;
import com.gabor.partypeps.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PartyPepsTestConfiguration {

    @Bean("userService")
    public UserService userService() {
        return new UserService();
    }

    @Bean("userMapper")
    public UserMapper userMapper(){ return new UserMapper(); }

    @Bean("messageService")
    public MessageService messageService(){
        return new MessageService();
    }

    @Bean("messageMapper")
    public MessageMapper messageMapper() { return new MessageMapper(); }

}
