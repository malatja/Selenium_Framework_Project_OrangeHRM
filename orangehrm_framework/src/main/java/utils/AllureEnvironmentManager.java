package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class AllureEnvironmentManager {

    private static final String ALLURE_RESULTS_DIRECTORY =
            System.getProperty("user.dir")
                    + "/target/allure-report/allure-results";


    private AllureEnvironmentManager() {
        // Prevent object creation
    }


    public static void createEnvironmentFile() {

        try {

            Path resultsDirectory =
                    Paths.get(ALLURE_RESULTS_DIRECTORY);

            Files.createDirectories(
                    resultsDirectory
            );

            Properties properties =
                    new Properties();

            properties.setProperty(
                    "Project",
                    "OrangeHRM Automation Framework"
            );

            properties.setProperty(
                    "Automation.Tool",
                    "Selenium WebDriver"
            );

            properties.setProperty(
                    "Test.Framework",
                    "TestNG"
            );

            properties.setProperty(
                    "Browser",
                    System.getProperty(
                            "browser",
                            "chrome"
                    )
            );

            properties.setProperty(
                    "Java.Version",
                    System.getProperty("java.version")
            );

            properties.setProperty(
                    "Operating.System",
                    System.getProperty("os.name")
            );

            properties.setProperty(
                    "Environment",
                    System.getProperty(
                            "environment",
                            "QA"
                    )
            );

            Path environmentFile =
                    resultsDirectory.resolve(
                            "environment.properties"
                    );

            try (FileOutputStream outputStream =
                         new FileOutputStream(
                                 environmentFile.toFile()
                         )) {

                properties.store(
                        outputStream,
                        "OrangeHRM Test Environment"
                );
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to create Allure environment file.",
                    e
            );
        }
    }
}