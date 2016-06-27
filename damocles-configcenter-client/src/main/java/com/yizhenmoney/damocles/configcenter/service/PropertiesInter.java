package com.yizhenmoney.damocles.configcenter.service;

import java.util.Properties;

import com.yizhenmoney.damocles.configcenter.vo.Token;

public interface PropertiesInter {
	
	public Token getToken(String token) throws Exception ;
	
	public void getProperties(Token token,Properties props) throws Exception;

}
