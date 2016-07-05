package com.yizhenmoney.damocles.configcenter.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * 左侧主菜单
 *
 */
public class MenuVo2 {

	// private long id;
	private String text;
	private String state = "closed";
	private Attributes attributes;

	@SuppressWarnings("unused")
	private class Attributes {

		public Attributes(String url, String type, String allPath) {
			this.url = url;
			this.type = type;
			this.allPath = allPath;
		}

		private String allPath;

		private String url;

		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getAllPath() {
			return allPath;
		}

		public void setAllPath(String allPath) {
			this.allPath = allPath;
		}

	}

	private List<MenuVo2> children = new LinkedList<MenuVo2>();

	public MenuVo2() {
	}

	public MenuVo2(String text, String type, String allPath) {
		this.text = text;
		this.attributes = new Attributes(null, type, allPath + "." + text);
	}

	public MenuVo2(String text, String url, String type, String allPath) {
		this.state = "open";
		this.text = text;
		this.attributes = new Attributes(url, type, allPath + "." + text);
	}

	public static MenuVo2 createTreeNode(String text, String type, String allPath) {
		return new MenuVo2(text, type, allPath);
	}

	public static MenuVo2 createLeafNode(String text, String url, String type, String allPath) {
		return new MenuVo2(text, url, type, allPath);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public List<MenuVo2> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVo2> children) {
		this.children = children;
	}

}
