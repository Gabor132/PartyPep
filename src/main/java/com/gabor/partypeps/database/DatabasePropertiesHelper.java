package com.gabor.partypeps.database;

import com.gabor.partypeps.enums.ProfilesEnum;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabasePropertiesHelper {


    private static final String JDBC_PROPERTIES_FILE_NAME = "jdbc.properties";

    public static Properties filterProperties(ProfilesEnum profile, Properties properties) {
        Properties newProperties = new Properties();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            Object objectKey = keys.nextElement();
            if (objectKey instanceof String) {
                String key = (String) objectKey;
                if (key.contains(profile.value.toLowerCase())) {
                    String newKey = key.replace(profile.value.toLowerCase() + ".", "");
                    newKey = newKey.replace("jdbc.", "");
                    newProperties.setProperty(newKey, properties.getProperty(key));
                }
            }
        }
        return newProperties;
    }

    public static Properties getJDBCProperties(){
        return getJDBCProperties(false, null);
    }

    public static Properties getJDBCProperties(boolean filtered, ProfilesEnum profile) {
        Properties prop = new Properties();
        try (FileInputStream file = new FileInputStream(JDBC_PROPERTIES_FILE_NAME)) {
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
