package com.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
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
		} else if (System.getProperty("browser").contains("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			System.out.println("Edge WebDriver initialized.");
		}
//		WebDriverManager.chromedriver().setup();
//		driver = new ChromeDriver();
		driver.get(System.getProperty("url"));
		driver.get("https://www.google.com/");

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
