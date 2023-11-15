package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.openqa.selenium.By;
import org.slf4j.Logger;

public class BasketPage extends BasePage{

	static final Logger LOG = getLogger(lookup().lookupClass());
	
	private static final By BY_LINK_CHECKOUT = By.cssSelector("a[href='/checkout']");
	private static final By BY_CONTAINER = By.className("content-container");
	
	public void checkout() {
		LOG.info("");
		//click title to close hoovered frame
		we.click(BY_CONTAINER);
		
		we.click(BY_LINK_CHECKOUT);
	}

	
	public void waitUntilVisible() {
		LOG.info("");
		we.waitUntilVisible(BY_LINK_CHECKOUT, 10 * 1000);
	}
}
