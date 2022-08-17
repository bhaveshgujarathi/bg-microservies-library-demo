package com.bhavesh.microservices_demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@InjectMocks
	private BookService booksService;

	@Mock
	private BooksRepository bookRepo;

	@Test
	public void getBookkDetails() {

		Book book = new Book();
		Optional<Book> optBook = Optional.of(book);
		when(bookRepo.findById(any())).thenReturn(optBook);
		Book bookResp=booksService.getBookDetails("");
		assertNotNull(bookResp);
		verify(bookRepo, times(1)).findById("");
	
	}

}
