package com.yizhenmoney.damocles.configcenter.service.sever;

import java.util.Map;

public interface PropertiesServerInter {

	/**
	 * 增加系统
	 * 
	 * @param system
	 * @throws Exception
	 */
	public void addSystem(String system) throws Exception;

	/**
	 * 增加版本
	 * 
	 * @param system
	 * @param version
	 * @throws Exception
	 */
	public void addVersion(String system, String version) throws Exception;

	/**
	 * 增加环境
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @param memo
	 * @param tag
	 * @throws Exception
	 */
	public void addEnv(String system, String version, String env, String memo, String tag) throws Exception;

	/**
	 * 增加属性
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @param properties
	 * @throws Exception
	 */
	public void addProperties(String system, String version, String env, Map<String, String> properties)
			throws Exception;

}
