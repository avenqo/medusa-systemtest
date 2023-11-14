package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.avenqo.medusa.fe.selenium.test.data.Customer;
import com.avenqo.medusa.fe.selenium.test.util.WebDriverProvider;

public class CheckoutPage {

	static final Logger LOG = getLogger(lookup().lookupClass());
	private final WebDriver driver = WebDriverProvider.getDriver();

	private static final By BY_BTN_PAYMENT = By.xpath("//button[text()='Select a payment method']");
	private static final By BY_BTN_DELIVERY = By.xpath("//button[text()='Continue to delivery']");

	private static final By BY_SELECTION_DELIVERY = By.cssSelector("div[role='radiogroup']");
	private static final By BY_BTN_CHECKOUT = By.xpath("//button[text()='Checkout']");;
	
	public void waitUntilVisible() {
		LOG.info("");
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(BY_BTN_PAYMENT));
	}

	public void enterMinimalAddress(Customer customer) {
		LOG.info("Customer [" + customer.toString() + "]");
		List<WebElement> inputs = driver.findElements(By.tagName("input"));

		this.clearAndEnter(inputs.get(0), customer.getEmail());
		this.clearAndEnter(inputs.get(1), customer.getFirstName());
		this.clearAndEnter(inputs.get(2), customer.getLastName());
		this.clearAndEnter(inputs.get(4), customer.getAddress().getStreet());
		this.clearAndEnter(inputs.get(6), customer.getAddress().getCode());
		this.clearAndEnter(inputs.get(7), customer.getAddress().getCity());

		this.clearAndEnter(inputs.get(10), customer.getPhone());
	}

	public void enterMinimalAddressAndContinue(Customer customer) {
		LOG.info("");
		this.enterMinimalAddress(customer);
		driver.findElement(BY_BTN_DELIVERY).click();
	}

	public void selectDeliveryService(String service) {
		LOG.info("");
		WebElement selection = driver.findElement(BY_SELECTION_DELIVERY);
		
		if (service.toLowerCase().contains("standard")) 
			selection = selection.findElements(By.tagName("div")).get(0);
		else if (service.toLowerCase().contains("express"))
			selection = selection.findElements(By.tagName("div")).get(1);
		else
			throw new RuntimeException("Don't know to handle '" + service + "'.");
		
		// Otherwise the click would lead to an Intercepted Exception.
		// A wait would be an alternative but not a nice one ...
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", selection);
	
	}

	public void pushCheckout() {
		LOG.info("");
		driver.findElement(BY_BTN_CHECKOUT).click();
	}

	private void clearAndEnter(WebElement input, String txt) {
		input.clear();
		input.sendKeys(txt);
	}
}
