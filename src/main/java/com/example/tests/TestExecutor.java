package com.example.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestExecutor {
    public static void main(String[] args) throws Exception {
        String testCase = System.getProperty("testcase", "runall");
        List<BaseTest> testsToRun = new ArrayList<>();

        switch (testCase.toLowerCase()) {
            case "tc001":
                testsToRun.add(new TC001());
                break;
            case "tc002":
                testsToRun.add(new TC002()); // if exists
                break;
            case "runall":
            default:
                testsToRun.add(new TC001());
                testsToRun.add(new TC002());
                break;
        }

        JSONArray reportArray = new JSONArray();
        int passed = 0, failed = 0, skipped = 0;

        for (BaseTest test : testsToRun) {
            JSONObject result = test.runTest();
            reportArray.put(result);

            JSONArray steps = result.getJSONArray("steps");
            boolean testFailed = false;

            for (int i = 0; i < steps.length(); i++) {
                JSONObject step = steps.getJSONObject(i);
                String status = step.optString("status");
                if ("failed".equalsIgnoreCase(status)) {
                    testFailed = true;
                    break;
                }
            }

            if (testFailed) {
                failed++;
            } else {
                passed++;
            }
        }

        JSONObject summary = new JSONObject();
        summary.put("passed", passed);
        summary.put("failed", failed);
        summary.put("skipped", skipped); // optional, based on actual use

        JSONObject finalReport = new JSONObject();
        finalReport.put("summary", summary);
        finalReport.put("details", reportArray);

        try (FileWriter file = new FileWriter("src/main/resources/static/report.json")) {
            file.write(finalReport.toString(4));
            System.out.println("✅ Saved consolidated report.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}