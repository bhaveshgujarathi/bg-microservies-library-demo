package com.bhavesh.microservices_demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
	
	@MockBean
	BookService bookService;

	@Autowired
	MockMvc mockMvc;
	
	
	
	@Test
	public void testFindBookDetails() throws Exception{
		Book book= new Book();
		book.setAuthor("bhavesh");
		
		Mockito.when(bookService.getBookDetails(any())).thenReturn(book);
		
		mockMvc.perform(get("/books/12"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.author", Matchers.is("bhavesh")));
		verify(bookService, times(1)).getBookDetails("12");		
	}
	
	
	
}
