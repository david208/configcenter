package com.yizhenmoney.damocles.configcenter.vo;

import java.io.Serializable;

public class PropertyInfo implements Serializable {

	/**
	 * \
	 */
	private static final long serialVersionUID = 6420175833245289166L;

	private String value;// 值

	private String memo;// 备注

	private String tag;// 标签

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
