package com.example.tests;

public class TC002 extends BaseTest {
    @Override
    protected void executeTest() throws Exception {
        logStep("Starting TC002 test", "passed");

        // Example assertion usage
        assertTrueWithLog(true, "Sample assertion always true");

        // Simulate some steps with assertions
        Thread.sleep(500);
        logStep("Step 1 completed", "passed");

        // Example failing assertion to demonstrate logging (uncomment to test failure)
        // assertTrueWithLog(false, "This step should fail");

        Thread.sleep(300);
        logStep("Step 2 completed", "passed");

        // Example equality assertion
        assertEqualsWithLog("expectedValue", "expectedValue", "Checking if values are equal");
    }
}
