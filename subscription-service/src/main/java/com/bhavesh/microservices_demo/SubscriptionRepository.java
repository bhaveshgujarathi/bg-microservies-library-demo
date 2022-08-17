package com.bhavesh.microservices_demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
	
	
	List<Subscription> findBySubscriberName(String subName);
	
	

}
