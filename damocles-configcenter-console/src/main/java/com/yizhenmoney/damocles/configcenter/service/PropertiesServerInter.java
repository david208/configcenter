package com.yizhenmoney.damocles.configcenter.service;

import java.util.List;
import java.util.Map;

import org.apache.curator.framework.api.transaction.CuratorTransactionFinal;
import org.apache.zookeeper.data.ACL;

import com.yizhenmoney.damocles.configcenter.vo.EnvInfo;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;

public interface PropertiesServerInter {

	/**
	 * 增加系统
	 * 
	 * @param system
	 * @param parentTransactionFinal
	 *            TODO
	 * @throws Exception
	 */
	public void addSystem(String system, CuratorTransactionFinal parentTransactionFinal) throws Exception;

	/**
	 * 增加版本
	 * 
	 * @param system
	 * @param version
	 * @param parentTransactionFinal
	 *            TODO
	 * @throws Exception
	 */
	public void addVersion(String system, String version, CuratorTransactionFinal parentTransactionFinal)
			throws Exception;

	/**
	 * 增加环境
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @param memo
	 * @param parentTransactionFinal
	 *            TODO
	 * @param tag
	 * @return TODO
	 * @throws Exception
	 */
	public List<ACL> addEnv(String system, String version, String env, String memo,
			CuratorTransactionFinal parentTransactionFinal) throws Exception;

	/**
	 * 增加属性
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @param properties
	 * @param curatorTransactionFinal
	 *            TODO
	 * @throws Exception
	 */
	public void addProperties(String system, String version, String env, Map<String, PropertyInfo> properties,
			CuratorTransactionFinal curatorTransactionFinal) throws Exception;

	/**
	 * 获取系统列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<String> getSystemList() throws Exception;

	/**
	 * 获取版本列表
	 * 
	 * @param system
	 * @return
	 * @throws Exception
	 */
	public List<String> getVersionList(String system) throws Exception;

	/**
	 * 获取环境列表
	 * 
	 * @param system
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public List<String> getEnvList(String system, String version) throws Exception;

	/**
	 * 获取环境的属性map
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @return
	 * @throws Exception
	 */
	public EnvInfo getProperties(String system, String version, String env) throws Exception;

	/**
	 * @param system
	 * @throws Exception
	 */
	public void deleteSystem(String system) throws Exception;

	/**
	 * @param system
	 * @param version
	 * @throws Exception
	 */
	public void deleteVersion(String system, String version) throws Exception;

	/**
	 * 删除环境
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @throws Exception
	 */
	public void deleteEnv(String system, String version, String env) throws Exception;

	/**
	 * 删除属性
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @param key
	 * @throws Exception
	 */
	public void deleteProperty(String system, String version, String env, String key) throws Exception;

	/**
	 * 修改环境
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @param memo
	 * @throws Exception
	 */
	public void editEnv(String system, String version, String env, String memo) throws Exception;

	/**
	 * 修改属性
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @param key
	 * @param propertyInfo
	 * @throws Exception
	 */
	public void editProperty(String system, String version, String env, String key, PropertyInfo propertyInfo)
			throws Exception;

	/**
	 * 复制版本
	 * 
	 * @param system
	 * @param version
	 * @param newVersion
	 * @throws Exception
	 */
	public void copyVersion(String system, String version, String newVersion) throws Exception;

	/**
	 * 复制环境
	 * 
	 * @param system
	 * @param version
	 * @param env
	 * @param newEnv
	 * @throws Exception
	 */
	public void copyEnv(String system, String version, String env, String newEnv) throws Exception;
	
	
	
	public EnvInfo getEnv(String system, String version, String env) throws Exception;
	
	public List<PropertyInfo> getPropertyInfos(String system, String version, String env) throws Exception;

}
