package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    private static final String CONFIG_PATH =
            "src/test/resources/config/config.properties";

    static {
        loadConfig();
    }

    /**
     * Loads the configuration properties file.
     */
    private static void loadConfig() {

        try (FileInputStream inputStream =
                     new FileInputStream(CONFIG_PATH)) {

            properties.load(inputStream);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load config.properties from: "
                            + CONFIG_PATH,
                    e
            );
        }
    }

    /**
     * Gets a property value using the specified key.
     */
    public static String getProperty(String key) {

        String value = properties.getProperty(key);

        if (value == null) {
            throw new RuntimeException(
                    "Property not found in config.properties: " + key
            );
        }

        return value.trim();
    }
}