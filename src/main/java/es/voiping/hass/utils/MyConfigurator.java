package es.voiping.hass.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyConfigurator {
    private static final Logger logger = Logger.getLogger("HASS Configurator");

    private static String configFile = "/aplicaciones/hass/config/hass-node-controller.properties";
    private static String configLogFile = "/aplicaciones/hass/config/hass-node-controller-log.properties";

    public static Properties loadProperties(String propertiesFile) {
        if (propertiesFile != null && !propertiesFile.equals("")) configFile = propertiesFile;

        Properties properties = new Properties();

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configFile);
            properties.load(inputStream);

            return properties;

        } catch (Exception e) {
            MyStackTrace.print(e);

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();

                } catch (IOException e) {
                    MyStackTrace.print(e);
                }
            }
        }

        logger.warn("No configuration is uploaded so the system is stopping");
        System.exit(-1);

        return null;
    }
}
