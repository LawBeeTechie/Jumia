package com.api.test;

import com.utils.Role;
import com.utils.TestUtility;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class JobAssignmentAPITest {
	private Headers headers;

	@BeforeMethod (description = "Setting up the baseURI")
	public void setup() {
		baseURI = "http://139.59.91.96:9000/v1";
		

		Header headers1 = new Header("Authorization", TestUtility.getAuthToken(Role.ENG));
		Header headers2 = new Header("Content-Type", "application/json");
		headers = new Headers(headers1, headers2);
		
	}
	@Test (description = "verify if JobAssignment api assigns job and,"
			+ " returns 200 status code with message as Engineer assigned successfully", groups = {"e2e", "smoke","sanity"}, priority = 1 )
	
	public void test_jobAssignment_api() {
	String message =	given()
						.headers(headers)
						.body(TestUtility.jobAssignmentRequest())
						.when()
						.log()
						.all()
						.post("engineer/assign")
						.then()
						.log().all()
						.assertThat()
						.statusCode(200)
						.and()
						.extract()
						.body()
						.jsonPath().
						getString("message");
						Assert.assertEquals(message, "Engineer assigned successfully");
						
	}
}
