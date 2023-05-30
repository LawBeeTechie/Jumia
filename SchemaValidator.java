package com.api.test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.pojo.LoginPojo;
import com.utils.TestUtility;

import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class SchemaValidator {

	private String loginRequestBody;
	
	@BeforeMethod (description = "Setting up the baserURI and the login POJO")
	public void setup() {
		baseURI = "http://139.59.91.96:9000/v1"; 
		 LoginPojo loginpojo = new LoginPojo("iamfd", "password");
		 loginRequestBody = TestUtility.toJson(loginpojo);	 
	}
	
	@Test(description = "verify if login api works for from desk and returns 200 status code with message as success", groups = {"e2e", "smoke",
			"sanity"}, priority = 1 )//, retryAnalyzer = com.utils.MyRetryAnalyzer.class)
	/*
	 * Status code 
	 * Status Time
	 * Response Body
	 * Schema Validation
	 * 
	 */
	public void test_login_api() {
			String message = given()
			 				.header(new Header("Content-Type", "application/json"))
			 				.body(loginRequestBody).when().log().all().post("/login").then().log().all()
							.assertThat().statusCode(200).and()
							.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("loginResponseSchema.json"))							
							.and().extract().body().jsonPath().
							getString("message");
							Assert.assertEquals(message, "Success");
		
	}
}
