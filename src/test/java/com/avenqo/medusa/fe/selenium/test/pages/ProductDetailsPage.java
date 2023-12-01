package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;

public class ProductDetailsPage extends BasePage{

	static final Logger LOG = getLogger(lookup().lookupClass());

	private static final By BY_PRODUCTINFO = By.cssSelector("#product-info");
	private static final By BY_BTN_ADD2CART = By.xpath("//button[.='Add to cart']");
	
	private static final By BY_PRODUCTNAME = By.xpath("//*[@id=\"product-info\"]/div/div/div/h3");
	private static final By BY_PRICE = By.xpath("//*[@id=\"product-info\"]/div/div/div/div[2]/div/span");
	
	public void addToCart() {
		LOG.info("");
		we.find(BY_PRODUCTINFO)
			.findElement(BY_BTN_ADD2CART)
			.click();
	}

	public void waitUntilVisible() {
		LOG.info("");

		we.createWebDriverWaitSec(10)
				.until(ExpectedConditions.presenceOfElementLocated(BY_PRODUCTINFO));
	}

	/**
	 * Click on relevant button for variant and wait until item is marked as selected.
	 * 
	 * @param v
	 */
	public void selectVariant(String v) {
		LOG.info("variant : [{}]", v);
		// Click button and
		final By BY_BTN = By.xpath("//button[.='" + v + "']");
		WebElement element = we.find(BY_BTN);
		we.jsClick(element);

		// ... wait until field is marked
		Wait<WebDriver> wait = we.createWebDriverWaitMs(500);
		wait.until(d -> element.getAttribute("class").contains("border-gray-900"));
	}

	public String getProductName() {
		LOG.info("");
		String productName = we.getText(BY_PRODUCTNAME);
		return productName;
	}

	public String getPrice() {
		LOG.info("");
		String price = we.getText(BY_PRICE);
		return price;
	}
}
