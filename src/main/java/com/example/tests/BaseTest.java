package com.example.tests;

import java.time.Duration;
import java.time.Instant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

public abstract class BaseTest {
    protected final JSONArray steps = new JSONArray();
    protected Instant testStartTime;

    public JSONObject runTest() {
        testStartTime = Instant.now();
        JSONObject testResult = new JSONObject();
        try {
            executeTest();
        } catch (Exception e) {
            logStep("Test failed with exception: " + e.getMessage(), "failed");
        }
        Instant testEndTime = Instant.now();
        long totalDurationMs = Duration.between(testStartTime, testEndTime).toMillis();

        try {
            testResult.put("testCase", this.getClass().getSimpleName());
            testResult.put("steps", steps);
            testResult.put("total_duration_ms", totalDurationMs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testResult;
    }

    protected abstract void executeTest() throws Exception;

    protected void logStep(String stepDescription, String status) {
        Instant stepEnd = Instant.now();
        long durationMs = 0;
        if (testStartTime != null) {
            durationMs = Duration.between(testStartTime, stepEnd).toMillis();
        }
        try {
            JSONObject stepObj = new JSONObject()
                    .put("step", stepDescription)
                    .put("status", status)
                    .put("duration_ms", durationMs);

            steps.put(stepObj);
            System.out.println(stepDescription + " - " + status + " (" + durationMs + " ms)");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // === Assertion Helpers with Logging without TestNG ===

    protected void assertTrueWithLog(boolean condition, String message) {
        if (condition) {
            logStep("Assertion Passed: " + message, "passed");
        } else {
            logStep("Assertion Failed: " + message, "failed");
            throw new AssertionError(message);
        }
    }

    protected void assertEqualsWithLog(Object actual, Object expected, String message) {
        boolean equal = (actual == null && expected == null) || (actual != null && actual.equals(expected));
        if (equal) {
            logStep("Assertion Passed: " + message, "passed");
        } else {
            String failMsg = message + ". Expected: " + expected + ", but was: " + actual;
            logStep("Assertion Failed: " + failMsg, "failed");
            throw new AssertionError(failMsg);
        }
    }

    protected void assertElementDisplayed(WebElement element, String description) {
        try {
            if (element.isDisplayed()) {
                logStep("Assertion Passed: " + description + " is displayed", "passed");
            } else {
                logStep("Assertion Failed: " + description + " is not displayed", "failed");
                throw new AssertionError(description + " is not displayed");
            }
        } catch (Exception e) {
            logStep("Assertion Failed: Exception checking " + description + " - " + e.getMessage(), "failed");
            throw new AssertionError(description + " check failed with exception: " + e.getMessage());
        }
    }
}
