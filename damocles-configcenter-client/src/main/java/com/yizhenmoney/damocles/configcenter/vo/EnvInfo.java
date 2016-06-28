package com.yizhenmoney.damocles.configcenter.vo;

import java.io.Serializable;

public class EnvInfo implements Serializable {

	/**
	 * \
	 */
	private static final long serialVersionUID = 6420175833245289166L;

	private String token;

	private String memo;

	private String tag;

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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	
}
