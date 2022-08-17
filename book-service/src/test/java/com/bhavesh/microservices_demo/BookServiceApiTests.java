package com.bhavesh.microservices_demo;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

public class BookServiceApiTests {

	@Test
	public void testGETBooks() {

		Response response = given().get("http://localhost:8765/book-service/books");
		assertEquals(200, response.getStatusCode());
		assertEquals(2, response.getBody().jsonPath().getList("").size());
	}
	
	@Test
	public void testGETBookDetails() {

		Response response = given().get("http://localhost:8765/book-service/books/1001");
		assertEquals(200, response.getStatusCode());
		 Book b=response.getBody().jsonPath().getObject("", Book.class);
		 
		assertEquals("1001",b.getBookId());
	}

}
