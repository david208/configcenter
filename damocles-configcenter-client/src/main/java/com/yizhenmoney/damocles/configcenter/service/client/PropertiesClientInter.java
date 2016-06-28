package com.yizhenmoney.damocles.configcenter.service.client;

import java.util.Properties;

import com.yizhenmoney.damocles.configcenter.vo.Token;

/**
 * @author shim
 *
 */
public interface PropertiesClientInter {

	/**
	 * 获取token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Token getToken(String token) throws Exception;

	/**
	 * 获取配置项
	 * 
	 * @param token
	 * @param props
	 * @throws Exception
	 */
	public void getProperties(Token token, Properties props) throws Exception;

}
