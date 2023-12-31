package com.avenqo.medusa.fe.selenium.test.util;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

class WebDriverProvider {
	
	private static WebDriver driver;
	private static WebDriverProvider webDriverProvider;
	
	
	private PropertyProvider propertyProvider;
	
	private static DriverType driverType;
	private static EnvironmentType environmentType;

	private WebDriverProvider() {
		propertyProvider = new PropertyProvider();
		driverType = propertyProvider.getDriverType();
		environmentType = propertyProvider.getEnvironmentType();
	}

	// --- static ---
	
	public static WebDriver getDriver() {
		if (webDriverProvider == null)
			webDriverProvider = new WebDriverProvider();
		if (driver == null)
			driver = webDriverProvider.createDriver();
		return driver;
	}

	public static void quitDriver() {
		if (driver != null) {
			//driver.close();
			driver.quit();
			driver = null;
		}
	}
	
	private WebDriver createDriver() {
		switch (environmentType) {
		case LOCAL:
			driver = createLocalDriver();
			break;
		case REMOTE:
			driver = createRemoteDriver();
			break;
		}
		return driver;
	}
	
	// --- private ---

	private WebDriver createRemoteDriver() {
		throw new RuntimeException("RemoteWebDriver is not yet implemented");
	}

	private WebDriver createLocalDriver() {
		switch (driverType) {
		case FIREFOX:
			driver = new FirefoxDriver();
			break;
		case CHROME:
			driver = new ChromeDriver();
			break;
		case EDGE:
			driver = new EdgeDriver();
			break;
		case SAFARI:
			driver = new SafariDriver();
			break;
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

		return driver;
	}
}
