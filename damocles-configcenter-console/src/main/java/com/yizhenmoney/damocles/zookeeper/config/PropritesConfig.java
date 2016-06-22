package com.yizhenmoney.damocles.zookeeper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件一些常量信息
 * */
@Component
public class PropritesConfig {
	private @Value("${zoo.nameSpace}") String zooNameSpace;
	private @Value("${zoo.paths}") String zooPaths;
	private @Value("${zoo.auth}") String zooAuth;
	private @Value("${zoo.loginAuth}") String AuthLoginUser;
	private @Value("${zoo.url}") String url;
	public String getZooAuth() {
		return zooAuth;
	}
	
	public String getUrl() {
		return url;
	}

	public String getAuthLoginUser() {
		return AuthLoginUser;
	}

	public String getZooNameSpace() {
		return zooNameSpace;
	}
	public String getZooPaths() {
		return zooPaths;
	}
	
	
}
