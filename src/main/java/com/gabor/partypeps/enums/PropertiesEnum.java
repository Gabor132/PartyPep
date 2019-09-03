package com.gabor.partypeps.enums;

/**
 * TODO: MOVE HERE ALL THE DAMN POSSIBLE PROPERTIES FROM ALL THE FILES AND USE THIS ENUM
 */
public enum PropertiesEnum{
    FROM_ENV_KEY("isFromEnv"),
    JDBC_DRIVER("driver"),
    JDBC_USER("user"),
    JDBC_PASSWORD("password"),
    JDBC_URL("url"),
    URL_URL("url"),
    SECURITY_SIGNING_KEY("signingKey"),
    SECURITY_CLIENT_ID("client.id"),
    SECURITY_SECRET("secret"),
    SECURITY_TEST_USERNAME("test.username"),
    SECURITY_TEST_PASSWORD("test.password");
    String value;
    PropertiesEnum(String value){
        this.value = value;
    }
    public String getValue(){ return value; }
}