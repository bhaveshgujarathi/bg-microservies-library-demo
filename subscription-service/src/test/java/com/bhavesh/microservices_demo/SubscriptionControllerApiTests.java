package com.bhavesh.microservices_demo;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

public class SubscriptionControllerApiTests {

	@Test
	public void testAddSubscription() {

		SubscriptionDto sb = new SubscriptionDto();
		sb.setBookId("1001");
		sb.setSubscriberName("bhavesh");
		sb.setDateSubscribed(new Date(2022, 02, 20));

		Response response = (Response) given().accept("application/json").contentType("application/json").body(sb)
				.post("http://localhost:8765/subscription-service/subscriptions");
		assertEquals(201, response.getStatusCode());

		// failure
		sb.setBookId("10012");
		response = (Response) given().accept("application/json").contentType("application/json").body(sb)
				.post("http://localhost:8765/subscription-service/subscriptions");
		assertEquals(422, response.getStatusCode());

	}

	@Test
	public void testGetSubscriptions() {

		Response response = (Response) given().accept("application/json")
				.get("http://localhost:8765/subscription-service/subscriptions?subName=");
		assertEquals(200, response.getStatusCode());

	}
}
