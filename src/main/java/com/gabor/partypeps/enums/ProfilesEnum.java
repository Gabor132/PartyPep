package com.gabor.partypeps.enums;

public enum ProfilesEnum {

    DEV("DEV"),
    IT("IT"),
    PROD("PROD");

    public final String value;

    ProfilesEnum(String value) {
        this.value = value;
    }
}
