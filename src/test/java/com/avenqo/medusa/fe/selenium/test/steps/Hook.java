package com.avenqo.medusa.fe.selenium.test.steps;

public class Hook {
/*
	static final Logger LOG = getLogger(lookup().lookupClass());

	// i.e. "http://localhost:8000/store"
	private final String URL_STORE;
	
	public Hook() {
		URL_STORE = new PropertyProvider().getAutUrlAsString();
	}
	
	/**
	 * Runs before the first step of each scenario.
	 * /
	@Before
	public void createDriver() {
		LOG.info("");
		final WebDriver driver = WebDriverProvider.getDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
		driver.get(URL_STORE);
	}

	/**
	 * Runs after the last step of each scenario, even when the step result is
	 * failed, undefined, pending, or skipped.
	 * 
	 * @param scenario
	 * /
	@After
	public void deleteDriver(Scenario scenario) {
		LOG.info("");
		WebDriverProvider.quitDriver();
	}
	
	
	@After
	public void afterScenario(Scenario scenario) throws Throwable {
		LOG.info("Scenario '{}' [Feature '{}']", scenario.getName(), scenario.getId());

		String featureName = scenario.getId();
		if (scenario.isFailed()) {
			LOG.error("Creating screenshot for feature '{}' and scenario '{}'.", featureName, scenario.getName());

			
			
			try {
				String fName = TEnv.getDeviceName();
			
				ScreenShotMaker ssm = new ScreenShotMaker(TEnv.getDriver4CurrentTEnvConfig());
				ssm.createPageSourceShot(fName);
				ssm.createScreenshot(fName);

				/*
				 * File srcFile = appiumDriver.getScreenshotAs(OutputType.FILE);
				 * 
				 * byte[] screenshot = ((TakesScreenshot)
				 * driver).getScreenshotAs(OutputType.BYTES); String testName =
				 * scenario.getName(); scenario.embed(screenshot, "image/png");
				 * scenario.write(testName);
				 * /
			} catch (Exception ex) {
				LOG.error("Screenshot creation failed.", ex);
				throw ex;
			}

		} else {
			LOG.info("------------------------------------------------------------------------");
			LOG.info("Finished: Feature '{}' and scenario '{}'.", featureName, scenario.getName());
			LOG.info("========================================================================");
		}
	}
	*/
}
