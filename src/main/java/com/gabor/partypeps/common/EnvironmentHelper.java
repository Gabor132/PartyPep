package com.gabor.partypeps.common;

import com.gabor.partypeps.enums.ProfilesEnum;

public abstract class EnvironmentHelper {

    private ProfilesEnum profilesEnum;

    public abstract ProfilesEnum getEnvironment();
}
