package com.yizhenmoney.damocles.configcenter.vo;

import java.io.Serializable;

public class Token implements Serializable {

	/**
	 * \
	 */
	private static final long serialVersionUID = 6420175833245289166L;

	private String name;// token的短名称

	private String auth;// 认证

	private String path;// 路径

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
