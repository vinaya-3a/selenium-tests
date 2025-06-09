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
    private static final String TC001 = "tc001";
    private static final String TC002 = "tc002";
    private static final String RUN_ALL = "runall";

    public static void main(String[] args) throws Exception {
        String testCase = System.getProperty("testcase", RUN_ALL).toLowerCase();
        List<BaseTest> testsToRun = new ArrayList<>();

        switch (testCase) {
            case TC001:
                testsToRun.add(new TC001());
                break;
            case TC002:
                testsToRun.add(new TC002());
                break;
            case RUN_ALL:
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
            boolean testSkipped = false;

            for (int i = 0; i < steps.length(); i++) {
                String status = steps.getJSONObject(i).optString("status", "").toLowerCase();
                if ("failed".equals(status)) {
                    testFailed = true;
                    break;
                }
                if ("skipped".equals(status)) {
                    testSkipped = true;
                }
            }

            if (testFailed) failed++;
            else if (testSkipped) skipped++;
            else passed++;
        }

        JSONObject summary = new JSONObject();
        summary.put("passed", passed);
        summary.put("failed", failed);
        summary.put("skipped", skipped);

        JSONObject finalReport = new JSONObject();
        finalReport.put("summary", summary);
        finalReport.put("details", reportArray);

        Files.createDirectories(Paths.get("reports"));

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());

        String normalizedName = (testCase.equals(TC001) || testCase.equals(TC002)) ? testCase : RUN_ALL;

        String reportFileName = "reports/" + normalizedName + "-" + timestamp + ".json";

        try (FileWriter file = new FileWriter(reportFileName)) {
            file.write(finalReport.toString(4));
            System.out.println("✅ Saved report to: " + reportFileName);
            System.out.println("REPORT_FILE_NAME=" + reportFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Optionally print summary
        System.out.printf("Tests run: %d, Passed: %d, Failed: %d, Skipped: %d%n", 
                          testsToRun.size(), passed, failed, skipped);

        System.exit(0);
    }
}
