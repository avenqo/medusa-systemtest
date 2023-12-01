package com.avenqo.medusa.fe.selenium.test.data;

import lombok.Data;

/**
 * The aim of this class is to keep the glue code (step definitions) free of
 * business test data. All test data being shared over steps (and sometimes
 * scenarios) is stored at this class.
 */
@Data
public class TestDataProvider {

	private Customer currentCustomer;
	private Order currentOrder;

	private static TestDataProvider testDataProvider;

	private TestDataProvider() {
		currentCustomer = new Customer();
		currentOrder = new Order();
	}

	public static TestDataProvider get() {
		if (testDataProvider == null)
			testDataProvider = new TestDataProvider();

		return testDataProvider;
	}

	public void initOrder() {
		currentOrder = new Order();
	}
}
