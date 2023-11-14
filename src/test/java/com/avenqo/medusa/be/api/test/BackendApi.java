package com.avenqo.medusa.be.api.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;

class BackendApi {

	private static Cookie authCookie;
	
	private static String BASE_URI = "http://localhost:9000/";
	private static String ADMIN_LOGIN = "admin@medusa-test.com";
	private static String PWD_ADMIN = "supersecret";
	
	protected BackendApi() {}
	
	private Cookie init() {
		RestAssured.baseURI = BASE_URI;
		
		JSONObject data = new JSONObject(); 
		data.put("password", PWD_ADMIN);
		data.put("email", ADMIN_LOGIN); 
		
		String cookie = RestAssured
    	.given()
	        .contentType(ContentType.JSON)
	        .body(data.toString())
	        
	        .log().all()

        .when()
        	.post("admin/auth")
        
        .then()
        	.log().all()
        	
        	.assertThat()
        	.statusCode(200)
        	.extract().cookie("connect.sid");
		
		System.out.println("Cookie 1 '" + cookie + "'");
		
		return new Cookie.Builder("connect.sid", cookie)
			      .setSecured(true)
			      .setComment("Medusa admin cookie")
			      .build();
	}

	protected Cookie getAuthCookie() {
		if (authCookie == null) {
			authCookie = init();
		}
		assertNotNull (authCookie);
		return authCookie;
	}
}
