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


    /**
     * Creates Allure environment.properties
     * with browser, suite and execution details.
     *
     * @param suiteName TestNG suite name
     */
    public static void createEnvironmentFile(String suiteName) {

        try {

            // Create Allure results directory
            Path resultsDirectory =
                    Paths.get(ALLURE_RESULTS_DIRECTORY);

            Files.createDirectories(resultsDirectory);


            Properties properties = new Properties();


            // Project information
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


            // Browser configuration
            String browser = System.getProperty(
                    "browser",
                    "chrome"
            );

            properties.setProperty(
                    "Browser",
                    browser
            );


            // Headless configuration
            boolean headless = Boolean.parseBoolean(
                    System.getProperty("headless", "false")
            );

            properties.setProperty(
                    "Headless",
                    String.valueOf(headless)
            );

            properties.setProperty(
                    "Execution.Mode",
                    headless ? "Headless" : "Normal"
            );


            // TestNG suite information
            properties.setProperty(
                    "Suite.Name",
                    suiteName != null && !suiteName.isBlank()
                            ? suiteName
                            : "Unknown Suite"
            );


            // Environment configuration
            properties.setProperty(
                    "Environment",
                    System.getProperty(
                            "environment",
                            "QA"
                    )
            );


            // System information
            properties.setProperty(
                    "Java.Version",
                    System.getProperty("java.version")
            );

            properties.setProperty(
                    "Operating.System",
                    System.getProperty("os.name")
            );


            // Create environment.properties
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


            System.out.println(
                    "ALLURE ENVIRONMENT FILE CREATED"
            );

            System.out.println(
                    "Browser: " + browser
            );

            System.out.println(
                    "Suite: " + suiteName
            );

            System.out.println(
                    "Execution Mode: "
                            + (headless ? "Headless" : "Normal")
            );


        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to create Allure environment file.",
                    e
            );
        }
    }
}