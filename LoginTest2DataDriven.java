package com.api.test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.pojo.LoginPojo;
import com.utils.TestUtility;

import io.restassured.http.Header;
import io.restassured.response.Response;

public class LoginTest2DataDriven { // Peforming Data driven test with Iteration

	private String loginRequestBody;
	
	@BeforeMethod (description = "Setting up the baserURI and the login POJO")
	public void setup() {
		baseURI = "http://139.59.91.96:9000/v1"; 
	
	}
	
	@Test(description = "verify if login api works for from desk and returns 200 status code with message as success", groups = {"e2e", "smoke",
			"sanity"}, priority = 1, dataProviderClass = com.dataprovider.LoginDataProvider.class, dataProvider= "loginDP" )//, retryAnalyzer = com.utils.MyRetryAnalyzer.class)
	
	public void test_login_api_with(String userName, String password ) {
		 LoginPojo loginpojo = new LoginPojo(userName, password);
		 loginRequestBody = TestUtility.toJson(loginpojo);	 
	
		
							String message = given()
			 				.header(new Header("Content-Type", "application/json"))
			 				.body(loginRequestBody)
			 			.when()	
			 			.log().all()
							.post("/login")
						.then()
						.log().all()
							.assertThat()
							.statusCode(200)
							.and()
							.extract()
							.body()
							.jsonPath().
							getString("message");
							Assert.assertEquals(message, "Success");
		
	}
}
