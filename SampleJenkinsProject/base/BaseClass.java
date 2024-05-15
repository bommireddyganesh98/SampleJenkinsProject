package com.mystore.base;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.mystore.actions.Action;
import com.mystore.utility.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	public static Properties prop;
//    public static WebDriver driver;
	public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

	@BeforeSuite(groups = { "Smoke", "Sanity", "Regression" })
	public void loadConfig() {
		try {
			ExtentManager.setExtent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMConfigurator.configure("log4j.xml");
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream(
					"C:\\Users\\bommi\\eclipse-workspace\\MyStoreProject\\Configuration\\Config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void launchApp(String browserName) {
		WebDriverManager.chromedriver().setup();
//        String browserName = prop.getProperty("browser");
		if (browserName == null) {
			System.out.println("Browser not specified in configuration. Exiting...");
			return; // Exit method if browser is not specified
		}

		if (browserName.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			System.out.println("Chrome WebDriver initialized.");
		} else if (browserName.contains("Edge")) {
//            driver = new EdgeDriver();
			driver.set(new EdgeDriver());

			System.out.println("Edge WebDriver initialized.");
		} else {
			System.out.println("Invalid browser specified in configuration. Exiting...");
			return; // Exit method if invalid browser is specified
		}

		if (driver == null) {
			System.out.println("WebDriver initialization failed. Exiting...");
			return; // Exit method if WebDriver initialization fails
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		Action.implicitWait(getDriver(), 10);
		String url = prop.getProperty("url");
		if (url == null) {
			System.out.println("URL not specified in configuration. Exiting...");
			return; // Exit method if URL is not specified
		}
		getDriver().get(url);
	}

	@AfterSuite
	public void afterSuite() {
		ExtentManager.endReport();
	}
}
