package com.yizhenmoney.damocles.configcenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yizhenmoney.damocles.configcenter.service.PropertiesServerInter;
import com.yizhenmoney.damocles.configcenter.service.PropertiesServerService;

@Configuration
public class ZooConfig {

	@Bean
	public PropertiesServerInter propertiesServerService(@Value("${zoo.admin.auth}") String adminAuth,
			@Value("${zoo.url}") String zooUrl) throws Exception {
		return new PropertiesServerService(adminAuth, zooUrl);

	}
}
