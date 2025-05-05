package hooks;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.AllureMetadataWriter;

import java.time.LocalDateTime;

public class Hooks implements ITestListener {
    private static boolean isMetadataWritten = false;

    @Override
    public void onStart(ITestContext context) {
        System.out.println("📋 Test suite started at: " + LocalDateTime.now());
        if (!isMetadataWritten) {
            AllureMetadataWriter.writeAllureMetadata();
            isMetadataWritten = true;
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🕒 Test started: " + result.getMethod().getMethodName() + " at " + LocalDateTime.now());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ Test failed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⚠️ Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🏁 Test suite finished at: " + LocalDateTime.now());
    }
}