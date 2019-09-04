package com.gabor.partypeps.configurations;

import com.gabor.partypeps.common.PropertiesHelper;
import com.gabor.partypeps.enums.ProfilesEnum;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import java.util.Properties;

@Profile("PROD")
@Configuration
@EnableAuthorizationServer
@ComponentScan({"com.gabor.partypeps.configurations", "com.gabor.partypeps.security"})
public class AuthorizationServerConfigurationPROD extends AuthorizationServerConfiguration{

    @Override
    protected Properties getSecurityProperties() {
        logger.info("USING PROD");
        return PropertiesHelper.getSecurityProperties(true, ProfilesEnum.PROD);
    }
}
