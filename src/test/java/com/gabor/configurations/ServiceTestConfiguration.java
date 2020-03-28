package com.gabor.configurations;

import com.gabor.partypeps.services.EventService;
import com.gabor.partypeps.services.GroupService;
import com.gabor.partypeps.services.MessageService;
import com.gabor.partypeps.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceTestConfiguration {

    @Bean("passwordEncoder")
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean("eventService")
    public EventService eventService() { return new EventService(); }

    @Bean("userService")
    public UserService userService() {
        return new UserService();
    }

    @Bean("messageService")
    public MessageService messageService() {
        return new MessageService();
    }

    @Bean("groupService")
    public GroupService groupService() {
        return new GroupService();
    }


}
