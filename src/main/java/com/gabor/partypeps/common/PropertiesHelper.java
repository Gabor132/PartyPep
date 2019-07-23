package com.gabor.partypeps.common;

import com.gabor.partypeps.database.AbstractDatabaseConfiguration;
import com.gabor.partypeps.enums.ProfilesEnum;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesHelper {


    private static final String JDBC_PROPERTIES_FILE_NAME = "jdbc.properties";
    private static final String URL_PROPERTIES_FILE_NAME = "url.properties";

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

    public static Properties getJDBCProperties() {
        return getJDBCProperties(false, null);
    }

    public static Properties getJDBCProperties(boolean filtered, ProfilesEnum profile) {
        return getProperties(JDBC_PROPERTIES_FILE_NAME, filtered, profile);
    }

    public static Properties getURLProperties(boolean filtered, ProfilesEnum profile) {
        return getProperties(URL_PROPERTIES_FILE_NAME, filtered, profile);
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
}
