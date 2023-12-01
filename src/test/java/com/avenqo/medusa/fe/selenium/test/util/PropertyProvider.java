package com.avenqo.medusa.fe.selenium.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProvider {

	private static final String CONFIG = "config.properties";

	private static final String KEY_DRIVER = "driver";
	private static final String KEY_ENVIRONMENT = "environment";
	private static final String URL_AUT = "AUT_URL";

	private static final String DRIVER_CHROME = "chrome";
	private static final String DRIVER_FIREFOX = "firefox";
	private static final String DRIVER_EDGE = "edge";
	private static final String DRIVER_SAFARI = "safari";

	private static final String ENV_LOCAL= "local";
	private static final String ENV_REMOTE = "remote";


	private String getProperty(String key) throws IOException {
		Properties properties = new Properties();
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG);
		properties.load(stream);
		return properties.getProperty(key).toLowerCase();
	}

	public DriverType getDriverType() {

		try {
			String strDriver = this.getProperty(KEY_DRIVER);
			if (strDriver != null && strDriver.length() > 0) {
				if (DRIVER_CHROME.equals(strDriver))
					return DriverType.CHROME;
				else if (DRIVER_FIREFOX.equals(strDriver))
					return DriverType.FIREFOX;
				else if (DRIVER_EDGE.equals(strDriver))
					return DriverType.EDGE;
				else if (DRIVER_SAFARI.equals(strDriver))
					return DriverType.SAFARI;
			}
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Failed to load properties for key '%s' in '%s'", KEY_DRIVER, CONFIG), e);
		}
		throw new RuntimeException(String.format("Missing key '%s' in '%s'", KEY_DRIVER, CONFIG));
	}
	
	public EnvironmentType getEnvironmentType() {

		try {
			String env = this.getProperty(KEY_ENVIRONMENT);
			if (env != null && env.length() > 0) {
				if (ENV_LOCAL.equals(env))
					return EnvironmentType.LOCAL;
				else if (ENV_REMOTE.equals(env))
					return EnvironmentType.REMOTE;	
			}
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Failed to load properties for key '%s' in '%s'", KEY_DRIVER, CONFIG), e);
		}
		throw new RuntimeException(String.format("Missing key '%s' in '%s'", KEY_DRIVER, CONFIG));
	}
	
	// TODO: make it simple!
	public String getAutUrlAsString() {

		try {
			String urlAut = this.getProperty(URL_AUT);
			if (urlAut == null || urlAut.length() <= 0) {
				throw new RuntimeException(String.format("Missing URL for Application Under Test, key '%s' in '%s'", KEY_DRIVER, CONFIG));
			}
			return urlAut;
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Failed to load properties for key '%s' in '%s'", KEY_DRIVER, CONFIG), e);
		}
	}
}