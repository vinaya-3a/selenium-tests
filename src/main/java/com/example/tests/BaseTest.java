package com.example.tests;

import java.time.Duration;
import java.time.Instant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseTest {
    protected final JSONArray steps = new JSONArray();
    protected Instant testStartTime;

    // Changed to return JSONObject with test results instead of writing files here
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

    // No more writing individual reports here
    // protected abstract String getReportFileName();  // Can be removed or ignored now
}