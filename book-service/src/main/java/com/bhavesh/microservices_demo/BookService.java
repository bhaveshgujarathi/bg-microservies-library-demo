package com.bhavesh.microservices_demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BooksRepository booksRepository;

	public List<Book> getBooks() {
		return booksRepository.findAll();
	}
	
	 

	public Book getBookDetails(String bookId) {
		Optional<Book> optBook = booksRepository.findById(bookId);
		if (optBook.isPresent()) {
			return optBook.get();
		} else {
			return null;
		}
	}
}
