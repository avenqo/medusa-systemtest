package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

public class LandingPage extends BasePage {

	static final Logger LOG = getLogger(lookup().lookupClass());
	

	public void selectProductByName(String productName) {

		LOG.info("productName: [{}]", productName);

		List<WebElement> productLinks = we.findElements(By.cssSelector("a[href^='/products/']"));
		assertTrue(productLinks != null);
		assertTrue(productLinks.size() > 0);

		WebElement matchingLink = null;
		for (WebElement linkElement : productLinks) {
			
			WebElement spanElement = linkElement.findElement(By.tagName("span"));
			String tmpProductName = we.getText(spanElement);
			//String tmpProductName = spanElement.getText();

			LOG.debug(String.format("Product Name (%s)", tmpProductName));
			if (productName.equals(tmpProductName)) {
				matchingLink = linkElement;
				break;
			}
		}

		assertNotNull(matchingLink, "Product name '" + productName + "' not found.");
		we.click(matchingLink);
		//matchingLink.click();
	}
}
