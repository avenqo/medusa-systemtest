package com.avenqo.medusa.fe.selenium.test.steps;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.avenqo.medusa.fe.selenium.test.util.PropertyProvider;
import com.avenqo.medusa.fe.selenium.test.util.WebDriverProvider;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hook {

	static final Logger LOG = getLogger(lookup().lookupClass());

	// i.e. "http://localhost:8000/store"
	private final String URL_STORE;
	
	public Hook() {
		URL_STORE = new PropertyProvider().getAutUrlAsString();
	}
	
	/**
	 * Runs before the first step of each scenario.
	 */
	@Before
	public void createDriver() {
		LOG.info("");
		final WebDriver driver = WebDriverProvider.getDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
		driver.get(URL_STORE);
	}

	/**
	 * Runs after the last step of each scenario, even when the step result is
	 * failed, undefined, pending, or skipped.
	 * 
	 * @param scenario
	 */
	@After
	public void deleteDriver(Scenario scenario) {
		LOG.info("");
		WebDriverProvider.quitDriver();
	}
}
