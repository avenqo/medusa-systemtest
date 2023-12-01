package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;

public class LandingPage extends BasePage {

	static final Logger LOG = getLogger(lookup().lookupClass());

	static final By BY_PRODUCTS = By.xpath("//main//a//span[.=]");

	public void selectProductByName(String productName) {
		LOG.info("productName: [{}]", productName);

		By BY_PRODUCTS = By.xpath("//main//a//span[.='" + productName + "']");

		List<WebElement> productLinks = we.findElements(BY_PRODUCTS);

		String errMsg = "Product not found [' + productname+ ']";
		assertNotNull(productLinks, errMsg);
		assertTrue(productLinks.size() > 0, errMsg);
		assertTrue(productLinks.size() < 2, "Product [' + productname+ '] isn't unique");
		productLinks.get(0).click();

		we.createWebDriverWaitSec(2).until(ExpectedConditions.elementToBeClickable(productLinks.get(0)));
		productLinks.get(0).click();

	}

	public void waitUntilVisible() {
		LOG.info("");
		we.createWebDriverWaitSec(10)
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href^='/products/'] img")));
	}
}
