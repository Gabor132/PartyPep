package com.gabor.configurations;

import com.gabor.partypeps.mappers.GroupMapper;
import com.gabor.partypeps.mappers.MessageMapper;
import com.gabor.partypeps.mappers.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperTestConfiguration {

    @Bean("userMapper")
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean("messageMapper")
    public MessageMapper messageMapper() {
        return new MessageMapper();
    }

    @Bean("groupMapper")
    public GroupMapper groupMapper() {
        return new GroupMapper();
    }
}
