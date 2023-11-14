package com.avenqo.medusa.fe.selenium.test.data;

import lombok.Data;

@Data
public class Customer {
	String firstName;
	String lastName;
	String email;
	String password;
	String phone;
	Address address;
	
	/**
	 * Restrict object creation to Factories.
	 */
	Customer(){}
}
