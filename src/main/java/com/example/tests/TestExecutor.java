package com.example.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestExecutor {

    public static void main(String[] args) throws IOException {
        String testCase = System.getProperty("testcase", "runall").toLowerCase();
        System.out.println("Running test case: " + testCase);

        List<JSONObject> allResults = new ArrayList<>();

        if ("tc001".equals(testCase) || "runall".equals(testCase)) {
            allResults.add(new TC001().runTest());
        }

        if ("tc002".equals(testCase) || "runall".equals(testCase)) {
            allResults.add(new TC002().runTest());
        }

        // Combine all test results into one JSON report
        JSONArray resultArray = new JSONArray(allResults);
        JSONObject finalReport = new JSONObject();
        finalReport.put("tests", resultArray);

        // Write the combined report JSON file
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(finalReport);

        Path path = Paths.get("src/main/resources/static/report.json");
        Files.createDirectories(path.getParent());
        Files.write(path, jsonContent.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Aggregated report written to: " + path.toAbsolutePath());
        System.exit(0);
    }
}
