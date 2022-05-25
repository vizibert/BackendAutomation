package com.geekbrains.herokuapp.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class ConfigUtils {
    Properties properties = new Properties();
    private static InputStream ConfigFile;

    static {
        try {
            ConfigFile = new FileInputStream("src/main/resources/com/geekbrains/herokuapp/application.properties");
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @SneakyThrows
    public String getBaseUrl() {
        properties.load(ConfigFile);
        return properties.getProperty("url");
    }

}
