package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import com.avenqo.medusa.fe.selenium.test.data.Customer;

public class CheckoutPage extends BasePage{

	static final Logger LOG = getLogger(lookup().lookupClass());
	
	private static final By BY_BTN_PAYMENT = By.xpath("//button[text()='Select a payment method']");
	private static final By BY_BTN_DELIVERY = By.xpath("//button[text()='Continue to delivery']");

	private static final By BY_SELECTION_DELIVERY = By.cssSelector("div[role='radiogroup']");
	private static final By BY_BTN_CHECKOUT = By.xpath("//button[text()='Checkout']");

	private static final By BY_ADDRESS = By.tagName("input");
	
	public void waitUntilVisible() {
		LOG.info("");
		// or presence?
		we.waitUntilPresence(BY_BTN_PAYMENT);
	}

	public void enterMinimalAddress(Customer customer) {
		LOG.info("Customer [" + customer.toString() + "]");
		List<WebElement> inputs = we.findElements(BY_ADDRESS);

		this.clearAndEnter(inputs.get(0), customer.getEmail());
		this.clearAndEnter(inputs.get(1), customer.getFirstName());
		this.clearAndEnter(inputs.get(2), customer.getLastName());
		this.clearAndEnter(inputs.get(4), customer.getAddress().getStreet());
		this.clearAndEnter(inputs.get(6), customer.getAddress().getCode());
		this.clearAndEnter(inputs.get(7), customer.getAddress().getCity());

		this.clearAndEnter(inputs.get(10), customer.getPhone());
	}

	public void enterMinimalAddressAndContinue(Customer customer) {
		LOG.info("Customer [" + customer.toString() + "]");
		this.enterMinimalAddress(customer);
		we.click(BY_BTN_DELIVERY);
	}

	public void selectDeliveryService(String service) {
		LOG.info("Service: [{}]", service);
		WebElement selection = we.find(BY_SELECTION_DELIVERY);
		
		if (service.toLowerCase().contains("standard")) 
			selection = selection.findElements(By.tagName("div")).get(0);
		else if (service.toLowerCase().contains("express"))
			selection = selection.findElements(By.tagName("div")).get(1);
		else
			throw new RuntimeException("Don't know to handle '" + service + "'.");
		
		// Otherwise the click would lead to an Intercepted Exception.
		// A wait would be an alternative but not a nice one ...
		we.jsClick(selection);
	}

	public void pushCheckout() {
		LOG.info("");
		we.click(BY_BTN_CHECKOUT);
	}

	private void clearAndEnter(WebElement input, String txt) {
		input.clear();
		input.sendKeys(txt);
	}
}
