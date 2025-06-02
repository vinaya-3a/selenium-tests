package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TC002Test {
    @Test
    public void testMethod() {
        String tc = System.getProperty("testcase");
        if ("RunAll".equalsIgnoreCase(tc) || "TC002".equalsIgnoreCase(tc)) {
            System.out.println("Running TC002");
            // test logic here
        }
    }
}