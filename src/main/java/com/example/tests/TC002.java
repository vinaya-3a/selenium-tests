package com.example.tests;

public class TC002 extends BaseTest{
	@Override
    protected void executeTest() throws Exception {
        logStep("Starting TC002 test", "passed");
        // Add your TC002 test steps here

        // Simulate some steps
        Thread.sleep(500);
        logStep("Step 1 completed", "passed");
        Thread.sleep(300);
        logStep("Step 2 completed", "passed");
        // ... add actual test logic
    }
}