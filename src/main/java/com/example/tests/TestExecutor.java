package com.example.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestExecutor {
    public static void main(String[] args) throws Exception {
        String testCase = System.getProperty("testcase", "RunAll");
        List<BaseTest> testsToRun = new ArrayList<>();

        switch (testCase.toLowerCase()) {
            case "tc001":
                testsToRun.add(new TC001());
                break;
            case "tc002":
                testsToRun.add(new TC002());
                break;
            case "runall":
            default:
                testsToRun.add(new TC001());
                testsToRun.add(new TC002());
                break;
        }

        JSONArray reportArray = new JSONArray();
        int passed = 0, failed = 0;

        for (BaseTest test : testsToRun) {
            JSONObject result = test.runTest();
            reportArray.put(result);

            JSONArray steps = result.getJSONArray("steps");
            boolean testFailed = false;

            for (int i = 0; i < steps.length(); i++) {
                JSONObject step = steps.getJSONObject(i);
                if ("failed".equalsIgnoreCase(step.optString("status"))) {
                    testFailed = true;
                    break;
                }
            }

            if (testFailed) failed++;
            else passed++;
        }

        JSONObject summary = new JSONObject();
        summary.put("passed", passed);
        summary.put("failed", failed);
        summary.put("skipped", 0);  // adjust as needed

        JSONObject finalReport = new JSONObject();
        finalReport.put("summary", summary);
        finalReport.put("details", reportArray);

        // ✅ Create reports folder if it doesn't exist
        Files.createDirectories(Paths.get("reports"));

        // ✅ Format timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());

        // ✅ Normalize test case name
        String normalizedName = testCase.toLowerCase();
        if (!normalizedName.equals("tc001") && !normalizedName.equals("tc002")) {
            normalizedName = "runall";
        }

        // ✅ Generate report file name
        String reportFileName = "reports/" + normalizedName + "-" + timestamp + ".json";

        // ✅ Write report to file
        try (FileWriter file = new FileWriter(reportFileName)) {
            file.write(finalReport.toString(4));
            System.out.println("✅ Saved report to: " + reportFileName);

            // ✅ Print filename for Spring Boot to capture
            System.out.println("REPORT_FILE_NAME=" + reportFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}