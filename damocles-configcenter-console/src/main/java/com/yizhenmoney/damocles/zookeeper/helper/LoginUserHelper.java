package com.yizhenmoney.damocles.zookeeper.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.yizhenmoney.damocles.zookeeper.config.PropritesConfig;

@Component
public class LoginUserHelper {
	private static PropritesConfig propritesConfig = (PropritesConfig) SpringContextHelper.getBean("propritesConfig");
	
	private static Map<String,Object> userMap =new HashMap<String, Object>();
	private static Map<String,Object> roleMap =new HashMap<String, Object>();
	static{
		init();
	}
	private static void init(){
		String auth = propritesConfig.getAuthLoginUser();
		String[] auths=auth.split(",");
		for (String user : auths) {
			//格式为username-password-权限
			String[] users = user.split("-");
			userMap.put(users[0], users[1]);
			roleMap.put(users[0], users[2]);
		}
	}
	public Map<String,Object> getAllUser(){
		return userMap;
	}
	public Map<String,Object> getAllRole(){
		return roleMap;
	}
	public  String getPassWordByUserName(String userName){
		return (String) userMap.get(userName);
	}
	public String getRole(String userName){
		return (String) roleMap.get(userName);
	}
	public String loginUserName(){
		Authentication au = SecurityContextHolder.getContext().getAuthentication();
		String userName="";
		if (au != null) {
			User user = (User) au.getPrincipal();
			userName = user.getUsername();
		}
		return userName;
	}
}
