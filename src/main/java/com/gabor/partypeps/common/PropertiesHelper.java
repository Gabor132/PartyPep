package com.gabor.partypeps.common;

import com.gabor.partypeps.database.AbstractDatabaseConfiguration;
import com.gabor.partypeps.enums.ProfilesEnum;
import com.gabor.partypeps.enums.PropertiesEnum;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesHelper {


    public static final String JDBC_PROPERTIES_FILE_NAME = "jdbc.properties";
    public static final String URL_PROPERTIES_FILE_NAME = "url.properties";
    public static final String SECURITY_PROPERTIES_FILE_NAME = "security.properties";

    public static Properties filterProperties(ProfilesEnum profile, Properties properties) {
        Properties newProperties = new Properties();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            Object objectKey = keys.nextElement();
            if (objectKey instanceof String) {
                String key = (String) objectKey;
                if (key.contains(profile.value.toLowerCase())) {
                    String newKey = key.replace(profile.value.toLowerCase() + ".", "");
                    newProperties.setProperty(newKey, properties.getProperty(key));
                }
            }
        }
        return newProperties;
    }

    public static Properties getJDBCProperties(boolean filtered, ProfilesEnum profile) {
        return getProperties(JDBC_PROPERTIES_FILE_NAME, filtered, profile);
    }

    public static Properties getURLProperties(boolean filtered, ProfilesEnum profile) {
        return getProperties(URL_PROPERTIES_FILE_NAME, filtered, profile);
    }

    public static Properties getSecurityProperties(boolean filtered, ProfilesEnum profile){
        return getProperties(SECURITY_PROPERTIES_FILE_NAME, filtered, profile);
    }

    public static Properties getProperties(String fileName, boolean filtered, ProfilesEnum profile) {

        Properties prop = new Properties();
        try (FileInputStream file = new FileInputStream(fileName)) {
            prop.load(file);
            if (filtered) {
                prop = filterProperties(profile, prop);
            }
        } catch (Exception e) {
            Logger.getLogger(AbstractDatabaseConfiguration.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return prop;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String getProperty(String fileName, ProfilesEnum profileEnum, PropertiesEnum propertyKey){
        return getProperty(getProperties(fileName, true, profileEnum), propertyKey);
    }

    public static String getProperty(Properties properties, PropertiesEnum propertyKey){
        return getProperty(properties, propertyKey.getValue());
    }

    public static String getProperty(Properties properties, String propertyKey){
        return getProperty(properties, propertyKey, "");
    }

    public static String getProperty(Properties properties, String propertyKey, String defaultValue){
        boolean isFromEnv = Boolean.parseBoolean(properties.getProperty(PropertiesEnum.FROM_ENV_KEY.getValue(), "false"));
        String value = isFromEnv ? System.getenv(properties.getProperty(propertyKey)) : properties.getProperty(propertyKey);
        return value == null || value.isEmpty() ? defaultValue : value;
    }

    public static String getProperty(String key, Properties properties, boolean isFromEnv) {
        return isFromEnv ? System.getenv(properties.getProperty(key)) : properties.getProperty(key);
    }
}
