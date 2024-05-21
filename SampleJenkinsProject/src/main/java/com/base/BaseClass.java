package com.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	WebDriver driver;

	@BeforeMethod
	@Parameters({ "browser", "url" })
	public void setup(String browser, String url) {
		if (browser == null || url == null) {
			throw new IllegalArgumentException("Browser and URL parameters cannot be null");
		}

		if (browser.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            // Try initially without headless to diagnose issues
            // options.addArguments("headless");
            options.addArguments("disable-gpu");
            driver = new EdgeDriver(options);
            System.out.println("Edge WebDriver initialized.");			
		} else if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			System.out.println("Chrome WebDriver initialized.");
		}
		else if(browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			System.out.println("Firefox WebDriver Initialized");
		}

		driver.get(url);
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
