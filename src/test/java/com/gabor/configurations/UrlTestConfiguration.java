package com.gabor.configurations;

import com.gabor.partypeps.common.PropertiesHelper;
import com.gabor.partypeps.enums.ProfilesEnum;
import com.gabor.partypeps.enums.PropertiesEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class UrlTestConfiguration {

    @Profile("DEV")
    @Bean("mainURL")
    public String getDEVURL() {
        return PropertiesHelper.getURLProperties(true, ProfilesEnum.DEV).getProperty(PropertiesEnum.URL_URL.getValue());
    }


    @Profile("IT")
    @Bean("mainURL")
    public String getITURL() {
        return PropertiesHelper.getURLProperties(true, ProfilesEnum.IT).getProperty(PropertiesEnum.URL_URL.getValue());
    }


    @Profile("PROD")
    @Bean("mainURL")
    public String getPRODURL() {
        return PropertiesHelper.getURLProperties(true, ProfilesEnum.PROD).getProperty(PropertiesEnum.URL_URL.getValue());
    }

}
