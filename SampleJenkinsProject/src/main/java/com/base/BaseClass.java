package com.base;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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

		switch (browser.toLowerCase()) {
		case "edge":
			setupEdgeDriver();
			break;
		case "chrome":
			setupChromeDriver();
			break;
		case "firefox":
			setupFirefoxDriver();
			break;
		default:
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}

		driver.get(url);
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	private void setupEdgeDriver() {
//		killEdgeProcesses(); // Kill any previous instances of Microsoft Edge

//		private void setupEdgeDriver() {
		// Set the path to the msedgedriver executable
		System.setProperty("webdriver.edge.driver", "C:\\Tools\\msedgedriver.exe");

		// Create EdgeOptions and add arguments
		EdgeOptions options = new EdgeOptions();
		options.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
//		options.addArguments("--no-sandbox"); // Bypass OS security model
//		options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
//		options.addArguments("--remote-debugging-port=0");
//		options.addArguments("--headless"); // No browser window

		// Initialize the Edge driver with options
		driver = new EdgeDriver(options);
		System.out.println("Edge WebDriver initialized.");

	}

	private void setupChromeDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		System.out.println("Chrome WebDriver initialized.");
	}

	private void setupFirefoxDriver() {
		// Set the path to the geckodriver executable
		System.setProperty("webdriver.gecko.driver", "C:\\Tools\\geckodriver.exe");

		// Create Firefox options
		FirefoxOptions options = new FirefoxOptions();

		// Set the path to the Firefox binary (if needed)
		options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

		// Initialize the Firefox driver with the options
		driver = new FirefoxDriver(options);
		System.out.println("Firefox WebDriver initialized.");
	}

	private void killEdgeProcesses() {
		try {
			Runtime.getRuntime().exec("taskkill /f /im msedge.exe");
			System.out.println("Previous instances of Microsoft Edge killed successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
