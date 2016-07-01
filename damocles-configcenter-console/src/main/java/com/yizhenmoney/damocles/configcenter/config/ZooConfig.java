package com.yizhenmoney.damocles.configcenter.config;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerInter;
import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerService;

@Configuration
public class ZooConfig {

	@Bean
	public PropertiesServerInter propertiesServerService(@Value("${zoo.admin.auth}") String adminAuth,
			@Value("${zoo.url}") String zooUrl) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return new PropertiesServerService(adminAuth, zooUrl);

	}
}
