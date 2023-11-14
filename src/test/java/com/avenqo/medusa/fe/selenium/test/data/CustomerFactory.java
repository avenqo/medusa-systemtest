package com.avenqo.medusa.fe.selenium.test.data;

import org.apache.commons.lang3.RandomStringUtils;

public class CustomerFactory {

	public static Customer getUnregisteredUser() {
		Customer customer = new Customer();
	
		String name = RandomStringUtils.random(8, true, false);
		customer.setEmail("a@" + name.toLowerCase() + ".de");
		customer.setFirstName("Ludwig Unregistriert");
		customer.setLastName(name);
		customer.setPassword("123");
		customer.setPhone("");
		
		Address address = new Address();
		address.setCity("Berlin");
		address.setCode("10179");
		address.setStreet("Sackgasse");
		customer.setAddress(address);
		
		return customer;
	}
}
