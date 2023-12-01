package com.avenqo.medusa.be.api.test;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.ValidatableResponse;

/**
 * @see https://docs.medusajs.com/api/store#orders_getordersorder
 */
public class OrderApi extends BackendApi{

	public ValidatableResponse getOrderById(String orderId) {

		Cookie cookie = super.getAuthCookie();
		
		// System.out.println("Cookie 2 '" + cookie.getValue() + "'");
		
		return RestAssured
			.given()
		        .contentType(ContentType.JSON)
		        .cookie(cookie)
		        
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
