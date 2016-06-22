package com.yizhenmoney.damocles.configcenter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestService {
	private @Value("${sms.username}") String username;
	
	public String getUsername(){
		return username;
	}

}
