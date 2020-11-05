package com.test.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadPropertiesFiles {

    public LoadPropertiesFiles() {
    }

    static String path = System.getProperty("user.dir")+"\\src\\main\\resources\\requestOTPUser.properties";

    public static Properties loadProperties() throws IOException {
        Properties prop = new Properties();
        InputStream ism = new FileInputStream(path);
        prop.load(ism);
        ism.close();
        return prop;
    }
    public static void saveProperties(String data) throws IOException {
        Properties props = new Properties();
        props.put("userName", data);

        FileOutputStream outputStrem = new FileOutputStream(path);
        props.store(outputStrem, "user created by Automation framework");
    }
}
