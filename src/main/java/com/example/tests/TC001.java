package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TC001 extends BaseTest{
	@Override
	protected void executeTest() throws Exception {
		logStep("Starting TC001", "passed");
		
		WebDriverManager.chromedriver().setup();
		System.setProperty("wdm.avoid-auto-download", "true");
		System.setProperty("wdm.cachePath", "drivers");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--headless=new");
		WebDriver driver = new ChromeDriver(options);

		logStep("Open browser", "passed");

		driver.get("https://www.saucedemo.com/");
		logStep("Navigated to login page", "passed");

		WebElement user = driver.findElement(By.id("user-name"));
		user.sendKeys("standard_user");
		logStep("Entered username", "passed");

		WebElement pass = driver.findElement(By.id("password"));
		pass.sendKeys("secret_sauce");
		logStep("Entered password", "passed");

		WebElement loginBtn = driver.findElement(By.id("login-button"));
		loginBtn.click();
		logStep("Clicked login", "passed");

		if (driver.getCurrentUrl().contains("inventory")) {
			logStep("Login successful", "passed");
		} else {
			logStep("Login failed", "failed");
		}

		driver.quit();
	}
}