package com.example.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseTest {
	protected final JSONArray steps = new JSONArray();
    protected Instant testStartTime;
    
    public void runTest() {
        testStartTime = Instant.now();
        try {
            executeTest();
            writeReport(getReportFileName());
        } catch (Exception e) {
            logStep("Test failed with exception: " + e.getMessage(), "failed");
            try {
                writeReport(getReportFileName());
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }
        }
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

    private void writeReport(String fileName) throws IOException, JSONException {
        Instant testEndTime = Instant.now();
        long totalDurationMs = Duration.between(testStartTime, testEndTime).toMillis();

        JSONObject report = new JSONObject();
        report.put("steps", steps);
        report.put("total_duration_ms", totalDurationMs);

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(report);

        Path path = Paths.get("src/main/resources/static/" + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, jsonContent.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        
        System.out.println("Report written to: " + path.toAbsolutePath());
    }

    // Each test should define its own unique report filename
    protected abstract String getReportFileName();
}
