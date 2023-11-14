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

public class BasketPage {

	static final Logger LOG = getLogger(lookup().lookupClass());
	private final WebDriver driver = WebDriverProvider.getDriver();
	
	private static final By BY_LINK_CHECKOUT = By.cssSelector("a[href='/checkout']");
	private static final By BY_CONTAINER = By.className("content-container");
	
	public void checkout() {
		LOG.info("");
		//click title to close hoovered frame
		driver.findElement(BY_CONTAINER).click();
		
		driver.findElement(BY_LINK_CHECKOUT).click();
	}

	
	public void waitUntilVisible() {
		LOG.info("");
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(BY_LINK_CHECKOUT));
	}
}
