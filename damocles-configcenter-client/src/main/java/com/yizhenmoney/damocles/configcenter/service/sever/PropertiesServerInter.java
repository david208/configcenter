package com.yizhenmoney.damocles.configcenter.service.sever;

import java.util.Map;
import java.util.Properties;

import com.yizhenmoney.damocles.configcenter.vo.Token;

public interface PropertiesServerInter {

	public void addSystem(String system) throws Exception;

	public void addVersion(String system, String version) throws Exception;

	public void addEnv(String system, String version, String env, String memo, String tag) throws Exception;

	public void addProperties(String system, String version, String env, Map<String, String> properties)
			throws Exception;

}
