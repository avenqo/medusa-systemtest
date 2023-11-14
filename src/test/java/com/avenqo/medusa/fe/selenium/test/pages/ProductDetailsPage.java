package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.avenqo.medusa.fe.selenium.test.util.WebDriverProvider;

public class ProductDetailsPage {

	static final Logger LOG = getLogger(lookup().lookupClass());

	private static final By BY_PRODUCTINFO = By.cssSelector("#product-info");
	private WebDriver driver = WebDriverProvider.getDriver();

	public void addToCart() {
		LOG.info("");
		driver.findElement(BY_PRODUCTINFO)
		.findElement(By.tagName("button"))
		.click();
	}

	public void waitUntilVisible() {
		LOG.info("");

		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(BY_PRODUCTINFO));
	}

}
