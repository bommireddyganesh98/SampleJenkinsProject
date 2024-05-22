package com.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.chromium.ChromiumDriverLogLevel;

public class BaseClass {
	WebDriver driver;

	@BeforeMethod
	@Parameters({ "browser", "url" })
	public void setup(String browser, String url) {
		if (browser == null || url == null) {
			throw new IllegalArgumentException("Browser and URL parameters cannot be null");
		}

		if (browser.equalsIgnoreCase("Edge")) {
			killEdgeProcesses(); // Kill any previous instances of Microsoft Edge

			WebDriverManager.edgedriver().setup();

			// Create EdgeDriverService with detailed configurations
			EdgeDriverService service = new EdgeDriverService.Builder().withLoglevel(ChromiumDriverLogLevel.DEBUG) // Set
																													// log
																													// level
																													// to
																													// DEBUG
					.withLogOutput(System.out) // Output logs to the console
					.withReadableTimestamp(true) // Timestamps in logs are readable
					// .usingPort(32123) // Optional: Specify the port if needed
					.build();

			// Create EdgeOptions and add arguments
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--no-sandbox"); // Bypass OS security model
			options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
			options.addArguments("--remote-debugging-port=0"); // Random port for remote debugging
			// options.addArguments("--remote-debugging-port=32123"); // Optional: Specify
			// the remote debugging port if needed
			options.addArguments("--headless"); // Run in headless mode (no browser window)

			// Start the Edge WebDriver with service and options
			driver = new EdgeDriver(service, options);
			System.out.println("Edge WebDriver initialized.");
		} else if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			System.out.println("Chrome WebDriver initialized.");
		} else if (browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			System.out.println("Firefox WebDriver initialized.");
		}

		driver.get(url);
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
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
