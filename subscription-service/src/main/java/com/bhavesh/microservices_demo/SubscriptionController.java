package com.bhavesh.microservices_demo;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@GetMapping("/subscriptions")
	public List<Subscription> getSubscriptions(@RequestParam("subName") String subName){
		
		return subscriptionService.getSubscriptions(subName);
	}
	@GetMapping("/subscriptions-circuitbreaker")
	public String getBookHystricksCheck(){
		
		return subscriptionService.getBooksServiceCheck();
	}
	
	
	@PostMapping("/subscriptions")
	public String addSubscription(@RequestBody SubscriptionDto subscriptionDto, HttpServletResponse response) {
		
		
		try {
			subscriptionService.addSubscription(subscriptionDto);
			response.setStatus(HttpServletResponse.SC_CREATED);	
		}catch (Exception e) {
			response.setStatus(422);
			return "failed";
		}
		
		return "success";
	}
	
}
