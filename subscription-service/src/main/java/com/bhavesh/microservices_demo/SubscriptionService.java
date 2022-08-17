package com.bhavesh.microservices_demo;

import java.nio.charset.Charset;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private BookServiceProxy bookServiceProxy;

	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

	public List<Subscription> getSubscriptions(String subName) {

		return StringUtils.isEmpty(subName) ? subscriptionRepository.findAll()
				: subscriptionRepository.findBySubscriberName(subName);
	}

	@Transactional
	public String addSubscription(SubscriptionDto subscriptionDto) throws RuntimeException {
		subscriptionDto.setDateReturned(new Date(subscriptionDto.getDateSubscribed().getYear(),
				subscriptionDto.getDateSubscribed().getMonth(), subscriptionDto.getDateSubscribed().getDay() + 30));

		Subscription sub = new Subscription();
		sub.setBookId(subscriptionDto.getBookId());
		sub.setDateReturned(subscriptionDto.getDateReturned());
		sub.setDateSubscribed(subscriptionDto.getDateSubscribed());
		sub.setSubscriberName(subscriptionDto.getSubscriberName());
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		sub.setSubId(generatedString);
		subscriptionRepository.save(sub);
			validateIfBookAvailable(subscriptionDto.getBookId());

		

	
		return "Success";

	}

	private void validateIfBookAvailable(String bookId) throws RuntimeException {
		HashMap<String, String> urlParams = new HashMap<>();
		urlParams.put("bookId", bookId);

		// using rest template
//		ResponseEntity<BookDto> bookDto = new RestTemplate()
//				.getForEntity("http://localhost:8765/book-service/books/{bookId}", BookDto.class, urlParams);

		// using feign client

		BookDto bookDto = bookServiceProxy.getBook(bookId);
		if (bookDto == null || bookDto.getAvailableCopies() == 0) {
			throw new RuntimeException();
		}

	}

	public String getBooksServiceCheck() {

		CircuitBreaker cb = circuitBreakerFactory.create("circuitbreaker");

		HashMap<String, String> urlParams = new HashMap<>();
		urlParams.put("bookId", "1001");
		try {
			cb.run(() -> new RestTemplate().getForEntity("http://localhost:8765/book-service/books/{bookId}",
					BookDto.class, urlParams), throwable -> callBookServiceAndGetData_Fallback("book service"));

		} catch (Exception e) {
			return "CIRCUIT BREAKER ENABLED!!! No Response From Book Service at this moment. "
					+ " Service will be back shortly - ";
		}

		return "Books service active";
	}

	private String callBookServiceAndGetData_Fallback(String schoolname) throws RuntimeException {

		System.out.println("Book Service is down!!! fallback route enabled...");

		throw new RuntimeException();
	}

}
