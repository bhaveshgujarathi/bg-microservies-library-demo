package com.bhavesh.microservices_demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/books")
	public List<Book> getBooks() {

		return bookService.getBooks();
	}
	
	@GetMapping("/books/{bookId}")
	public Book getBookDetails(@PathVariable("bookId") String bookId){
		
		return bookService.getBookDetails(bookId);
	}
}
