package com.avenqo.medusa.fe.selenium.test.data;

import lombok.Data;

@Data
public class DeliveryAddress {

	// First and last name
	String name;
	String address;
	String cityCode;
	String country;
	String deliveryMethod;
}
