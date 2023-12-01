package com.avenqo.medusa.fe.selenium.test.elements;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.avenqo.medusa.fe.selenium.test.util.WebDriverProvider;

/**
 * This class represents a WebElement. There are 2 intentions of this class:
 * 1. Provide a formatted logging output on "Element" level (beside steps, pages, ...)
 * 2. Wait for elements before interacting
 * 3. Convenience
 *  
 */
public class WE {

	private static final Logger LOG = getLogger(lookup().lookupClass());

	private static final int DEFAULT_TIMEOUT_MS = 2000;
	
	protected final WebDriver driver = WebDriverProvider.getDriver();
	protected JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
	
	public <T> List<WebElement> findElements(By by) {
		List<WebElement> elements = driver.findElements(by);
		LOG.info("Found [{}] elements for locator [{}]", elements != null ? elements.size() : "NULL", by.toString());
		return elements;
	}
	
	public <T> WebElement find(By by) {
		LOG.info("By [{}]", by);
		return driver.findElement(by);
	}
	
	
	/**
	 * @param <T> You can use both By or WebElement
	 * @param elementAttr
	 */
	public <T> void waitUntilVisible(T elementAttr) {
		LOG.info("WebElement: {}, using default timeout", elementAttr);
		waitUntilVisible(elementAttr, DEFAULT_TIMEOUT_MS);
    }
  
	public WebDriverWait createWebDriverWaitSec(int timeout_sec) {
		LOG.debug("timeout_sec: {}", timeout_sec);
		return new WebDriverWait(driver, Duration.ofSeconds(timeout_sec));	
	}
	
	public WebDriverWait createWebDriverWaitMs(int timeout_ms) {
		LOG.debug("timeout_ms: {}", timeout_ms);
		return new WebDriverWait(driver, Duration.ofMillis(timeout_ms));	
	}
	
	public <T> void waitUntilVisible(T elementAttr, int timeout_ms) {
		LOG.info("WebElement: {}, timeout_ms: {}", elementAttr);
		WebDriverWait wait = createWebDriverWaitMs(timeout_ms);
		
        if (isBy(elementAttr)) {
            wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementAttr));
        } else {
            wait.until(ExpectedConditions.visibilityOf((WebElement) elementAttr));
        }
    }
  
	
	public void waitUntilPresence(By by) {
		LOG.info("WebElement: {}", by);
		waitUntilPresence(by, DEFAULT_TIMEOUT_MS);
	}

	public void waitUntilPresence(By by, int timeout_ms) {
		LOG.info("By: {}", by);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout_ms));
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}
   
    /**
     * @param <T> You can use both By or WebElement
     * @param elementAttr
     */
    public <T> void click(T elementAttr) {
    	LOG.info("WebElement: {}", elementAttr);
        waitUntilVisible(elementAttr);
        if (isBy(elementAttr)) {
            driver
                .findElement((By) elementAttr)
                .click();
        } else {
            ((WebElement) elementAttr).click();
        }
    }
    
    
    public <T> void jsClick(T elementAttr) {
    	LOG.info("By: {}", elementAttr);
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(DEFAULT_TIMEOUT_MS));
    	if (isBy(elementAttr)) {
    		javascriptExecutor.executeScript("arguments[0].click();", wait.until(ExpectedConditions.visibilityOfElementLocated((By) elementAttr)));
        } else {
            javascriptExecutor.executeScript("arguments[0].click();", wait.until(ExpectedConditions.visibilityOf((WebElement) elementAttr)));
        }
    }
    
    
    /**
     * 
     * @param <T> You can use both By or WebElement
     * @param elementAttr
     * @param text
     */
    public <T> void clearAndEnterText(T elementAttr, String text) {
    	LOG.info("WebElement: {}, Text [{}]", elementAttr, text);
    	waitUntilVisible(elementAttr);
        if (isBy(elementAttr)) {
            driver
                .findElement((By) elementAttr)
                .sendKeys(text);
        } else {
            ((WebElement) elementAttr).sendKeys(text);
        }
    }

    
    /**
     * 
     * @param <T> You can use both By or WebElement
     * @param elementAttr
     * @return
     */
    public <T> String getText(T elementAttr) {
    	LOG.info("WebElement: {}", elementAttr);
    	String txt = null;
        if (isBy(elementAttr)) {
            txt = driver
                .findElement((By) elementAttr)
                .getText();
        } else {
            txt = ((WebElement) elementAttr).getText();
        }
        LOG.info("returning: [{}]", elementAttr);
        return txt;
    }
    
    
    private <T> boolean isBy(T elementAttr) {
		assertNotNull (elementAttr);
		if (elementAttr
            .getClass()
            .getName()
            .contains(".By")) {
            return true;
        } else {
        	if (elementAttr instanceof WebElement)
        		return false;
        }
		throw new RuntimeException(
				"Don't know how to handle element of type [" + elementAttr.getClass().getName() + "].");
    }

	public WebDriver getDriver() {
		return driver;
	}

	public void waitSomeTime(int time_ms) {
		try {
			Thread.sleep(time_ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void clickWithOffset(By by, int dx, int dy) {
		Actions actions = new Actions(driver);
		WebElement element = find(by);
		actions.moveToElement(element)
			.moveByOffset(dx, dy)
			.click()
			.build().perform();
	}
	
	public void hoover(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();;
	}
}
