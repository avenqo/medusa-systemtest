package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import com.avenqo.medusa.fe.selenium.test.util.WebDriverProvider;

public class LandingPage {

	static final Logger LOG = getLogger(lookup().lookupClass());
	private final WebDriver driver = WebDriverProvider.getDriver();

	public void selectProductByName(String productName) {

		LOG.info(String.format("productName (%s)", productName));

		List<WebElement> productLinks = driver.findElements(By.cssSelector("a[href^='/products/']"));
		assertTrue(productLinks != null);
		assertTrue(productLinks.size() > 0);

		WebElement matchingLink = null;
		for (WebElement linkElement : productLinks) {
			WebElement spanElement = linkElement.findElement(By.tagName("span"));
			String tmpProductName = spanElement.getText();

			LOG.debug(String.format("Product Name (%s)", tmpProductName));
			if (productName.equals(tmpProductName)) {
				matchingLink = linkElement;
				break;
			}
		}

		assertNotNull(matchingLink, "Product name '" + productName + "' not found.");
		matchingLink.click();

		/*
		 * productLinks.stream() .filter(link -> productName.equals(
		 * link.findElement(By.tagName("span")).getText()))
		 * .collect(Collectors.toList());
		 */
	}

}
