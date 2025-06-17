package com.example.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC002 extends BaseTest {
    @Override
    protected void executeTest() throws Exception {
        logStep("Starting TC002", "passed");

        WebDriverManager.chromedriver().setup();
        System.setProperty("wdm.avoid-auto-download", "true");
        System.setProperty("wdm.cachePath", "drivers");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        logStep("Open browser", "passed");

        try {
            driver.get("https://uat.qaconnector.com/Fintech/tenantLogin");
            logStep("Navigated to login page", "passed");

            try {
                WebElement loginBtnVisible = driver.findElement(By.id("Login_loginbtn__7Tj03"));
                assertElementDisplayed(loginBtnVisible, "Login button");
            } catch (Exception e) {
                logStep("Login button not found: " + e.getMessage(), "failed");
                throw e;
            }

            try {
                WebElement user = driver.findElement(By.name("user_name"));
                user.sendKeys("amith.nadig");
                logStep("Entered username", "passed");

                WebElement pass = driver.findElement(By.name("password"));
                pass.sendKeys("ViratKohli@123");
                logStep("Entered password", "passed");
            } catch (Exception e) {
                logStep("Failed to enter credentials: " + e.getMessage(), "failed");
                throw e;
            }

            try {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("Login_loginbtn__7Tj03")));
                WebElement loginBtn = driver.findElement(By.id("Login_loginbtn__7Tj03"));
                loginBtn.click();
                logStep("Clicked login", "passed");
            } catch (Exception e) {
                logStep("Login click failed: " + e.getMessage(), "failed");
                throw e;
            }

            try {
                wait.until(ExpectedConditions.urlContains("Fintech"));
                verifyTrueWithLog(driver.getCurrentUrl().contains("Vinay"),
                        "Login successful and navigated to Fintech Solutions page");
            } catch (Exception e) {
                logStep("URL validation failed after login: " + e.getMessage(), "failed");
                throw e;
            }

            try {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("landingPage_companyName__2RiNM")));
                WebElement inventoryList = driver.findElement(By.className("landingPage_companyName__2RiNM"));
                assertElementDisplayed(inventoryList, "Dashboard inventory element");
            } catch (Exception e) {
                logStep("Dashboard validation failed: " + e.getMessage(), "failed");
                throw e;
            }

        } finally {
            driver.quit();
            logStep("Closed browser", "passed");
        }
    }
}
