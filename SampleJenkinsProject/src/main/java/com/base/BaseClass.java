package com.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	WebDriver driver;

	@BeforeMethod
	public void setup() {
		if (System.getProperty("browser").equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			System.out.println("Chrome WebDriver initialized.");
		} else if (System.getProperty("browser").equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
			options.addArguments("headless");
			options.addArguments("disable-gpu");
			driver = new EdgeDriver(options);
			System.out.println("Edge WebDriver initialized.");
		}
//		WebDriverManager.chromedriver().setup();
//		driver = new ChromeDriver();
		String url = System.getProperty("url");
		if (url != null && !url.isEmpty()) {
			driver.get(url);
		} else {
			driver.get("https://www.google.com/");
		}
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
