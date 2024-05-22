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
		options.addArguments("--remote-debugging-pipe"); // Use pipe instead of port for remote debugging
		options.addArguments("--headless"); // Run in headless mode (no browser window)
		options.addArguments("--disable-gpu"); // Disable GPU to avoid potential issues
		options.addArguments("--disable-software-rasterizer"); // Disable software rasterizer
		options.addArguments("--disable-extensions"); // Disable extensions
		options.addArguments("--disable-background-networking"); // Disable background networking
		options.addArguments("--disable-background-timer-throttling"); // Disable background timer throttling
		options.addArguments("--disable-client-side-phishing-detection"); // Disable client-side phishing detection
		options.addArguments("--disable-default-apps"); // Disable default apps
		options.addArguments("--disable-hang-monitor"); // Disable hang monitor
		options.addArguments("--disable-popup-blocking"); // Disable popup blocking
		options.addArguments("--disable-prompt-on-repost"); // Disable prompt on repost
		options.addArguments("--disable-sync"); // Disable sync
		options.addArguments("--enable-automation"); // Enable automation
		options.addArguments("--enable-logging"); // Enable logging
		options.addArguments("--log-level=0"); // Set log level to 0 (INFO)
		options.addArguments("--no-first-run"); // Disable first run
		options.addArguments("--test-type=webdriver"); // Set test type to webdriver
		options.addArguments("--use-mock-keychain"); // Use mock keychain

		// Start the Edge WebDriver with service and options
		driver = new EdgeDriver(service, options);
		System.out.println("Edge WebDriver initialized.");
	}

	private void setupChromeDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		System.out.println("Chrome WebDriver initialized.");
	}

	private void setupFirefoxDriver() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
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
