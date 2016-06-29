package com.yizhenmoney.damocles.configcenter.vo;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Map;

public class EnvInfo implements Serializable {

	/**
	 * \
	 */
	private static final long serialVersionUID = 6420175833245289166L;

	private String token;// token的短名称

	private String memo;// 备注

	private String longToken;// 完整token

	private Map<String, PropertyInfo> properties;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Transient
	public Map<String, PropertyInfo> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, PropertyInfo> properties) {
		this.properties = properties;
	}

	@Transient
	public String getLongToken() {
		return longToken;
	}

	public void setLongToken(String longToken) {
		this.longToken = longToken;
	}

}
