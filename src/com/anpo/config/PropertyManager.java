package com.anpo.config;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

    static Properties properties = new Properties();

    static {
        try {
            properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("com/anpo/config/config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get (String key){
        if (properties == null){
            return null;
        }
        return properties.get(key);
    }

    public static Integer getInt(String key){
        return Integer.parseInt(getString(key));
    }

    public static String getString(String key){
        return String.valueOf(get(key));
    }

}
