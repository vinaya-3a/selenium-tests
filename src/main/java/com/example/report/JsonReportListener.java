package com.example.report;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class JsonReportListener implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    	System.out.println(123);
        Map<String, Object> report = new HashMap<>();
        List<Map<String, Object>> details = new ArrayList<>();
        int passed = 0, failed = 0, skipped = 0;

        for (ISuite suite : suites) {
            for (ISuiteResult result : suite.getResults().values()) {
                ITestContext context = result.getTestContext();

                passed += context.getPassedTests().size();
                failed += context.getFailedTests().size();
                skipped += context.getSkippedTests().size();

                addTestResults(context.getPassedTests().getAllResults(), "PASSED", details);
                addTestResults(context.getFailedTests().getAllResults(), "FAILED", details);
                addTestResults(context.getSkippedTests().getAllResults(), "SKIPPED", details);
            }
        }

        report.put("summary", Map.of(
                "total", passed + failed + skipped,
                "passed", passed,
                "failed", failed,
                "skipped", skipped
        ));
        report.put("details", details);

        try (Writer writer = new FileWriter(outputDirectory + "/report.json")) {
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(writer, report);
            System.out.println("✅ Generated report.json with test results.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTestResults(Set<ITestResult> results, String status, List<Map<String, Object>> details) {
        for (ITestResult result : results) {
            Map<String, Object> testDetail = new HashMap<>();
            testDetail.put("testCase", result.getName());
            testDetail.put("status", status);
            testDetail.put("duration", (result.getEndMillis() - result.getStartMillis()) / 1000.0);
            details.add(testDetail);
        }
    }
}
