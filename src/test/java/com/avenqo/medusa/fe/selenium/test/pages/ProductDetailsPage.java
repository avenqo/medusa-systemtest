package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	 * Click on relevant button and wait until item is marked as selected.
	 * 
	 * @param v
	 */
	public void selectVariant(String v) {
		LOG.info("variant : [{}]", v);
		// Click button and
		final By BY_BTN = By.xpath("//button[.='"+ v +"']");
		
		//we.createWebDriverWaitSec(1)
		//	.until(ExpectedConditions.elementToBeClickable(BY_BTN));
		WebElement element = we.find(BY_BTN);
		// element.click();*/
		we.jsClick(element);
		
		// ... wait until field is marked
		Wait<WebDriver> wait = we.createWebDriverWaitMs(500);
		wait.until(d ->element.getAttribute("class").contains("border-gray-900"));
	}

	public String getProductName() {
		String productName = we.find(BY_PRODUCTNAME).getText();
		LOG.debug("Produkt Name [{}]", productName);
		return productName;
	}

	public String getPrice() {
		String price = we.find(BY_PRICE).getText();
		LOG.debug("Preis [{}]", price);
		return price;
	}
}
