package com.yizhenmoney.damocles.configcenter.config;

import java.util.List;

public interface Config {
	public List<String> getConfig2(String path) throws Exception;

//	public List<String> getBaseConfig(String path) throws Exception;
}