package com.gabor.partypeps.common;

import com.gabor.partypeps.enums.ProfilesEnum;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("DEV")
@Component
public class EnvironmentHelperDEV extends EnvironmentHelper {


    @Override
    public ProfilesEnum getEnvironment() {
        return ProfilesEnum.DEV;
    }
}
