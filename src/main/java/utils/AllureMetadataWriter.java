package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AllureMetadataWriter {

    public static void writeAllureMetadata() {
        Properties props = new Properties();

        // Environment Info
        props.setProperty("Project Name", "QA Automation Suite");
        props.setProperty("Framework", "Playwright + TestNG + RestAssured");
        props.setProperty("Environment", "Staging");
        props.setProperty("Executed By", System.getProperty("user.name"));
        props.setProperty("Execution Time", java.time.LocalDateTime.now().toString());

        try {
            // Warning !  Section for writing allure reports
            File resultsDir = new File("target/surefire-reports");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }

            File envPropsFile = new File(resultsDir, "environment.properties");
            try (FileOutputStream fos = new FileOutputStream(envPropsFile)) {
                props.store(fos, "Allure Environment Properties");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
