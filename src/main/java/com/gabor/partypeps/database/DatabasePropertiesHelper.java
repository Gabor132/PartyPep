package com.gabor.partypeps.database;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabasePropertiesHelper {


    private static final String JDBC_PROPERTIES_FILE_NAME = "jdbc.properties";
    public static final String DB_USER_KEY = "user";
    public static final String DB_PASSWORD_KEY = "password";
    public static final String DB_URL_KEY = "url";

    public static Properties filterProperties(String profile, Properties properties) {
        Properties newProperties = new Properties();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            Object objectKey = keys.nextElement();
            if (objectKey instanceof String) {
                String key = (String) objectKey;
                if (key.contains(profile.toLowerCase())) {
                    String newKey = key.replace(profile.toLowerCase() + ".", "");
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

    public static Properties getJDBCProperties(boolean filtered, String profile) {
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
