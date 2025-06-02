package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TC001Test {
    @Test
    public void testMethod() {
        String tc = System.getProperty("testcase");
        if ("RunAll".equalsIgnoreCase(tc) || "TC001".equalsIgnoreCase(tc)) {
            System.out.println("Running TC001");
            // test logic here
        }
    }
}