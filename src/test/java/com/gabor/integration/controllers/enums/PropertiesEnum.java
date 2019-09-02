package com.gabor.integration.controllers.enums;

/**
 * TODO: MOVE HERE ALL THE DAMN POSSIBLE PROPERTIES FROM ALL THE FILES AND USE THIS ENUM
 */
public enum PropertiesEnum{
    FROM_ENV_KEY("isFromEnv"),
    USERNAME_KEY("username"),
    PASSWORD_KEY("password");
    String value;
    PropertiesEnum(String value){
        this.value = value;
    }
    public String getValue(){ return value; }
}
