package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TC003 {
    @Test
    public void testMethod() {
        String tc = System.getProperty("testcase");
        if ("RunAll".equalsIgnoreCase(tc) || "TC003".equalsIgnoreCase(tc)) {
            System.out.println("Running TC003");
            // test logic here
        }
    }
}