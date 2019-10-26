package com.gabor.partypeps.common.env;

import com.gabor.partypeps.enums.ProfilesEnum;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("PROD | default")
@Component
public class EnvironmentHelperPROD extends EnvironmentHelper {
    @Override
    public ProfilesEnum getEnvironment() {
        return ProfilesEnum.PROD;
    }
}
