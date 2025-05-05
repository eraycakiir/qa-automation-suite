// ✅ AllureMetadataWriter.java
package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AllureMetadataWriter {
    public static void writeAllureMetadata() {
        Properties props = new Properties();
        props.setProperty("Project Name", "QA Automation Suite");
        props.setProperty("Framework", "Playwright + TestNG + RestAssured");
        props.setProperty("Environment", "CI");
        props.setProperty("Executed By", System.getProperty("user.name"));
        props.setProperty("Execution Time", java.time.LocalDateTime.now().toString());

        try {
            File resultsDir = new File("allure-results");
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
