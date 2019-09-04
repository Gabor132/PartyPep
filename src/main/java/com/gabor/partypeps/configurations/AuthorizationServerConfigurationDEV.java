package com.gabor.partypeps.configurations;

import com.gabor.partypeps.common.PropertiesHelper;
import com.gabor.partypeps.enums.ProfilesEnum;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import java.util.Properties;

@Profile("DEV")
@Configuration
@EnableAuthorizationServer
@ComponentScan({"com.gabor.partypeps.configurations", "com.gabor.partypeps.security"})
public class AuthorizationServerConfigurationDEV extends AuthorizationServerConfiguration{

    @Override
    protected Properties getSecurityProperties() {
        logger.info("USING DEV");
        return PropertiesHelper.getSecurityProperties(true, ProfilesEnum.DEV);
    }
}
