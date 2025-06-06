package com.example.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestExecutor {
    public static void main(String[] args) throws Exception {
        String testCase = System.getProperty("testcase", "RunAll");
        List<BaseTest> testsToRun = new ArrayList<>();

        switch (testCase) {
            case "tc001":
                testsToRun.add(new TC001());
                break;
            case "tc002":
                testsToRun.add(new TC002()); // if exists
                break;
            case "RunAll":
            default:
                testsToRun.add(new TC001());
                // testsToRun.add(new TC002()); // Add more as needed
                break;
        }

        JSONArray reportArray = new JSONArray();
        for (BaseTest test : testsToRun) {
            JSONObject result = test.runTest();
            reportArray.put(result);
        }

        // Save to JSON file
        try (FileWriter file = new FileWriter("src/main/resources/static/report.json")) {
            file.write(reportArray.toString(4));
            System.out.println("✅ Saved report.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
