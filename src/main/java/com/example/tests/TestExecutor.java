package com.example.tests;

public class TestExecutor {

    public static void main(String[] args) {
        String testCase = System.getProperty("testcase", "runall").toLowerCase();
        System.out.println("Running test case: " + testCase);

        TC001 tc001 = new TC001();
        TC002 tc002 = new TC002();

        switch (testCase) {
            case "tc001":
                tc001.runTest();
                break;
            case "tc002":
                tc002.runTest();
                break;
            case "runall":
            default:
                tc001.runTest();
                tc002.runTest();
                break;
        }
        System.exit(0);
    }
}