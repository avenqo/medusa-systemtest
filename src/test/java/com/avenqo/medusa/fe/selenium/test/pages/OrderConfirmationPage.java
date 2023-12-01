package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import com.avenqo.medusa.fe.selenium.test.data.DeliveryAddress;
import com.avenqo.medusa.fe.selenium.test.data.OrderedArticle;

public class OrderConfirmationPage extends BasePage{

	static final Logger LOG = getLogger(lookup().lookupClass());
	
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
	private final static By BY_PROD_COLOR = By
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
	private final static By BY_SUM_TAX = By
			.xpath("/html/body/main/div[2]/div/div/div[3]/div[2]/div/div[2]/div[2]/span[2]");

	private static final String RETURNING = "Returning: {}";
	private static final By BY_ORDERID = By.xpath("/html/body/main/div[2]/div/div/div[1]/span[2]");

	public void waitUntilVisible() {
		LOG.info("");

		we.waitUntilPresence(BY_CONFIRMATION_MSG, 5);
		we.waitUntilVisible(BY_CONFIRMATION_MSG, 3);
	}
	
	// -----------------------------
	// parent of div kids representing products
	private final static By BY_PRODUCT = By.xpath("/html/body/main/div[2]/div/div/div[2]/div/div[2]/div/div");
	
	private final static By BY_NAME = By.xpath("./div[1]/h3/a");
	private final static By BY_PRICE2 = By.xpath("./div[2]/div/span");
	private final static By BY_ANZAHL = By.xpath("./div[1]/span");
	private final static By BY_OPTIONS = By.xpath("./div[1]/div/div/span");
	
	
	public List<OrderedArticle> getArticles() {
		List<OrderedArticle> articles = new ArrayList<>();
		
		// go through the list of ordered articles
		for (WebElement prodElements : we.findElements(BY_PRODUCT)) {
			final OrderedArticle article = new OrderedArticle();
			articles.add(article);

			// pick up article properties
			article.setName(prodElements.findElement(BY_NAME).getText());
			article.setNumber(extractOptionValueByName("Quantity", prodElements.findElement(BY_ANZAHL).getText()));
			article.setSummaryPrice(prodElements.findElement(BY_PRICE2).getText());
			// pickup article options
			List<WebElement> options = prodElements.findElements(BY_OPTIONS);
			if (options != null && options.size() > 0) {
				for (WebElement option : options) {
					// extract option text

					String[] optionStrings = extractOptions(option.getText());
					if (optionStrings[0].toLowerCase().contains("color"))
						article.setColor(optionStrings[1].trim());
					else if (optionStrings[0].toLowerCase().contains("size"))
						article.setSize(optionStrings[1].trim());
					else
						throw new RuntimeException(
								"Don't know how to handle articel option [" + optionStrings[0] + "].");

				}
			} else
				throw new RuntimeException("No options found. Expecting at least one option");
		}
		return articles;
	}

	private String extractOptionValueByName(String optionName, String txt) {
		String optionValue;
		if (txt != null && txt.contains(":")) {
			String[] splitted = txt.split(":");
			if (splitted.length == 2) {
				if (splitted[0].contains(optionName))
					optionValue = splitted[1].trim();
				else
					throw new RuntimeException("Don't know how to handle articel option [" + splitted[0] + "].");
			} else
				throw new RuntimeException(
						"Don't know how to handle articel option WebElement with text[" + optionName + "].");
		} else
			throw new RuntimeException("No option name ['" + optionName + "'] found in text [" + txt + "].");
		return optionValue;
	}

	private String[] extractOptions(String txt) {
		String[] option;
		if (txt != null && txt.contains(":")) {
			String[] splitted = txt.split(":");
			if (splitted.length == 2) {
				option = new String[2];
				option[0] = splitted[0].trim();
				option[1] = splitted[1].trim();
			} else
				throw new RuntimeException(
						"Don't know how to handle articel option WebElement with text[" + txt + "].");
		} else
			throw new RuntimeException("Cannot handle empty option text.");
		return option;
	}
	
	// -----------------------------

	public DeliveryAddress getDeliveryAddress() {
		LOG.info("");
		DeliveryAddress deliveryAddress = new DeliveryAddress();

		List<WebElement> spans = we.findElements(BY_CUSTOMER);

		deliveryAddress.setName(spans.get(0).getText());
		deliveryAddress.setAddress(spans.get(1).getText());
		deliveryAddress.setCityCode(spans.get(2).getText());
		deliveryAddress.setCountry(spans.get(3).getText());

		deliveryAddress.setDeliveryMethod(we.find(BY_DELIVERY).getText());

		return deliveryAddress;
	}

	// overview
	
	public String getArticle() {
		String s = we.find(BY_PROD_NAME).getText();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getSize() {
		String s = we.find(BY_PROD_SIZE).getText();
		s = s.substring(s.indexOf(":") + 1).trim();
		LOG.info(RETURNING, s);
		return s;
	}
	
	public String getColor() {
		String s = we.find(BY_PROD_COLOR).getText();
		s = s.substring(s.indexOf(":") + 1).trim();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getCount() {
		String s = we.find(BY_PROD_QUANTITY).getText();
		s = s.substring(s.indexOf(":") + 1).trim();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getPrice() {
		String s = we.find(BY_PROD_PRICE).getText();
		LOG.info(RETURNING, s);
		return s;
	}
	
	// summary

	
	public String getTax() {
		String s = we.find(BY_SUM_TAX).getText();
		s = StringUtils.isBlank(s) ? null : s;
		
		LOG.info(RETURNING, s);
		return s;
	}
	
	public String getTotal() {
		String s = we.find(BY_SUM_TOTAL).getText();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getSubtotal() {
		String s = we.find(BY_SUM_SUBTOTAL).getText();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getDeliveryPrice() {
		String s = we.find(BY_SUM_DELIVERY).getText();
		LOG.info(RETURNING, s);
		return s;
	}

	public String getOrderId() {
		String s = we.find(BY_ORDERID).getText();
		assertNotNull(s);
		LOG.info(RETURNING, s);
		return s;
	}
	
	
	
	
}
