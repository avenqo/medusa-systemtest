package com.avenqo.medusa.fe.selenium.test.pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.avenqo.testfacility.element.web.WE;

class BasePage {
	
	static final Logger LOG = getLogger(lookup().lookupClass());
	
	protected final static WE we = new WE();
	
	protected static final String RETURNING = "Returning: [{}]";
	
	protected WebDriverWait createWebDriverWait(int timeout_sec) {
		return new WebDriverWait(we.getDriver(), Duration.ofSeconds(timeout_sec));
	}

	public void refresh() {
		LOG.info("");
		we.getDriver().navigate().refresh();
	}
}
