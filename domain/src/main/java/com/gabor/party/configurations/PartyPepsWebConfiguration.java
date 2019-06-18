package com.gabor.party.configurations;

import com.gabor.party.services.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PartyPepsWebConfiguration {

    @Bean
    public MessageService messageService(){
        return new MessageService();
    }

}