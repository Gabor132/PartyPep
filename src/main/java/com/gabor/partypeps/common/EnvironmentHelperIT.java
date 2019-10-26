package com.gabor.partypeps.common;

import com.gabor.partypeps.enums.ProfilesEnum;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("IT")
@Component
public class EnvironmentHelperIT extends EnvironmentHelper{


    @Override
    public ProfilesEnum getEnvironment() {
        return ProfilesEnum.IT;
    }
}
