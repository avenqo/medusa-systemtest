package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.openqa.selenium.By;
import org.slf4j.Logger;

public class NavigationBar extends BasePage {

	static final Logger LOG = getLogger(lookup().lookupClass());

	private static final By BY_CART = By.partialLinkText("My Bag");

	public void gotoBag() {
		LOG.info("");
		we.find(BY_CART).click();
	}

	public int getNumberOfArticlesInBasket() {
		LOG.info("");

		String txt = we.getText(BY_CART);
		LOG.info("--------- '" + txt + "'");
		return Integer.valueOf(txt.replaceAll("[^0-9]", ""));
	}

	public void waitUntilBagIsContainingItems(int itemSize) {
		LOG.info("itemSize: [{}]");
		we.getWait().until(i -> getNumberOfArticlesInBasket() == itemSize);
	}
}
