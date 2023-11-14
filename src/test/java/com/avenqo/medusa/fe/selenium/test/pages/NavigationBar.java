package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.avenqo.medusa.fe.selenium.test.util.WebDriverProvider;

public class NavigationBar {

	static final Logger LOG = getLogger(lookup().lookupClass());

	private WebDriver driver = WebDriverProvider.getDriver();

	private static final By BY_CART = By.partialLinkText("My Bag");

	public void gotoBag() {
		LOG.info("");
		driver.findElement(BY_CART).click();
	}

	public int getNumberOfArticlesInBasket() {
		LOG.info("");
		WebElement bagElement = driver.findElement(BY_CART);

		LOG.info("--------- '" + bagElement.getText() + "'");
		return Integer.valueOf(bagElement.getText().replaceAll("[^0-9]", ""));
	}

	public void waitUntilBagIsContainingItems(int itemSize) {
		LOG.info("");

		new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(i -> getNumberOfArticlesInBasket() == itemSize);
	}
}
