package com.bhavesh.microservices_demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookServiceProxy {
	
	@GetMapping("books/{bookId}")
	public BookDto getBook(@PathVariable String bookId);
	

}
