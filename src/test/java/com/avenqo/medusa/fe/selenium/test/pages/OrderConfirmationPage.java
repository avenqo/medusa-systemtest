package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.avenqo.medusa.fe.selenium.test.data.DeliveryAddress;
import com.avenqo.medusa.fe.selenium.test.util.WebDriverProvider;

public class OrderConfirmationPage {

	static final Logger LOG = getLogger(lookup().lookupClass());
	private final WebDriver driver = WebDriverProvider.getDriver();

	private static final By BY_CONFIRMATION_MSG = By
			.xpath("//span[text()='Thank you, your order was successfully placed']");
	// private static final By BY_CUSTOMER = By.cssSelector("div.my-2 > div >
	// span");
	private static final By BY_CUSTOMER = By.cssSelector("div.my-2 > div:nth-of-type(1) > span");

	private static final By BY_DELIVERY = By.cssSelector("div.my-2:nth-of-type(2) > div > div");

	// Ordered Product
	private final static By BY_PROD_QUANTITY = By
			.xpath("/html/body/main/div[2]/div/div/div[2]/div/div[2]/div/div/div[1]/span");
	private final static By BY_PROD_SIZE = By
			.xpath("/html/body/main/div[2]/div/div/div[2]/div/div[2]/div/div/div[1]/div/div/span");
	private final static By BY_PROD_NAME = By
			.xpath("/html/body/main/div[2]/div/div/div[2]/div/div[2]/div/div/div[1]/h3/a");
	private final static By BY_PROD_PRICE = By
			.xpath("/html/body/main/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/div/span");
	
	private final static By BY_SUM_SUBTOTAL = By
			.xpath("/html/body/main/div[2]/div/div/div[3]/div[2]/div/div[1]/span[2]");
	private final static By BY_SUM_DELIVERY = By
			.xpath("/html/body/main/div[2]/div/div/div[3]/div[2]/div/div[2]/div[1]/span[2]");
	private final static By BY_SUM_TOTAL = By
			.xpath("/html/body/main/div[2]/div/div/div[3]/div[2]/div/div[4]/span[2]");

	private static final String RETURNING = "Returning: {}";
	private static final By BY_ORDERID = By.xpath("/html/body/main/div[2]/div/div/div[1]/span[2]");

	public void waitUntilVisible() {
		LOG.info("");

		new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(BY_CONFIRMATION_MSG));

		new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(ExpectedConditions.visibilityOf(driver.findElement(BY_CONFIRMATION_MSG)));
	}

	public DeliveryAddress getDeliveryAddress() {
		LOG.info("");
		DeliveryAddress deliveryAddress = new DeliveryAddress();

		List<WebElement> spans = driver.findElements(BY_CUSTOMER);

		deliveryAddress.setName(spans.get(0).getText());
		deliveryAddress.setAddress(spans.get(1).getText());
		deliveryAddress.setCityCode(spans.get(2).getText());
		deliveryAddress.setCountry(spans.get(3).getText());

		deliveryAddress.setDeliveryMethod(driver.findElement(BY_DELIVERY).getText());

		return deliveryAddress;
	}

	// overview
	
	public String getArticle() {
		String s = driver.findElement(BY_PROD_NAME).getText();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getVariant() {
		String s = driver.findElement(BY_PROD_SIZE).getText();
		s = s.substring(s.indexOf(":") + 1).trim();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getCount() {
		String s = driver.findElement(BY_PROD_QUANTITY).getText();
		s = s.substring(s.indexOf(":") + 1).trim();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getPrice() {
		String s = driver.findElement(BY_PROD_PRICE).getText();
		LOG.info(RETURNING, s);
		return s;
	}
	
	// summary

	public String getTotal() {
		String s = driver.findElement(BY_SUM_TOTAL).getText();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getSubtotal() {
		String s = driver.findElement(BY_SUM_SUBTOTAL).getText();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getDeliveryPrice() {
		String s = driver.findElement(BY_SUM_DELIVERY).getText();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getOrderId() {
		String s = driver.findElement(BY_ORDERID).getText();
		assertNotNull(s);
		LOG.info(RETURNING, s);
		return s;
	}
}
