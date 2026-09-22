package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LocatorReader {

    private static final Properties properties = new Properties();

    private static final String LOCATOR_PATH =
            "src/test/resources/config/locators.properties";

    static {
        loadLocators();
    }

    /**
     * Loads the locator properties file.
     */
    private static void loadLocators() {

        try (FileInputStream inputStream =
                     new FileInputStream(LOCATOR_PATH)) {

            properties.load(inputStream);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load locators.properties from: "
                            + LOCATOR_PATH,
                    e
            );
        }
    }

    /**
     * Gets a locator value using the specified key.
     */
    public static String getLocator(String key) {

        String locator = properties.getProperty(key);

        if (locator == null) {
            throw new RuntimeException(
                    "Locator not found in locators.properties: " + key
            );
        }

        return locator.trim();
    }
}