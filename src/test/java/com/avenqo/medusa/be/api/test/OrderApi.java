package com.avenqo.medusa.be.api.test;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

/**
 * @see https://docs.medusajs.com/api/store#orders_getordersorder
 */
public class OrderApi extends BackendApi{

	public ValidatableResponse getOrderById(String orderId) {

		System.out.println("Cookie 2 '" + super.getAuthCookie().getValue() + "'");
		
		return RestAssured
			.given()
		        .contentType(ContentType.JSON)
		        .cookie(super.getAuthCookie())
		        
		        .log().all()

	        .when()
	        	.get("/store/orders/" + orderId)
	        
	        .then()
	        	.log().all()
	        	.assertThat()
	        	.body("order.id", equalTo(orderId))
	        	.body("order.payments[0].order_id", equalTo(orderId))
	  
	        	.statusCode(200);
	}
}
