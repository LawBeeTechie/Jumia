package com.api.test;

import com.utils.TestUtility;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class SupLoginAPITest {

	
	@BeforeMethod (description = "Setting up the baseURI")
	public void setup() {
	 baseURI = "http://139.59.91.96:9000/v1/";
	
	 
	} 
	 @Test (description = "verify if Supvisor Login works and returns 200 status code with message as Success",
			 groups = {"e2e", "smoke","sanity"}, priority = 1 )
	 
	public void test_supLogin_api() {
	String message 		= given()
			 			.header("Content-Type", "application/json")
			 			.body(TestUtility.supLoginBodyPojo())
			 			.when().log().all()
			 			.post("login")
			 			.then()
			 			.log().all()
			 			.assertThat()
			 			.statusCode(200)
			 			.and().extract().body()
			 			.jsonPath()
			 			.getString("message");
	Assert.assertEquals(message, "Success");
			 			;
	
	 }
}
