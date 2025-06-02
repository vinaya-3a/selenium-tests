package com.example.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class TestExecutor {
    public static void main(String[] args) {
        String testCase = System.getProperty("testcases");
        System.out.println(testCase);

        List<Class<?>> testClasses = new ArrayList<>();

        switch (testCase) {
            case "tc001":
                testClasses.add(TC001.class);
                break;
            case "tc002":
                testClasses.add(TC002.class);
                break;
            case "tc003":
                testClasses.add(TC003.class);
                break;
            case "runall":
            default:
                testClasses.add(TC001.class);
                testClasses.add(TC002.class);
                testClasses.add(TC003.class);
                break;
        }

        TestNG testng = new TestNG();
        testng.setTestClasses(testClasses.toArray(new Class[0]));
        testng.run();
    }
}