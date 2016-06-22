package com.yizhenmoney.damocles.configcenter.vo;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 左侧主菜单
 *
 */
public class MenuVo {

	private long id;
	private String name;
	private String code;
	private String url;
	private int ord;
	
	private Set<MenuVo> children = new TreeSet<MenuVo>(new Comparator<MenuVo>() {
		@Override
		public int compare(MenuVo o1, MenuVo o2) {
			return o1.ord - o2.ord;
		}
	});

	public MenuVo() {
	}

	public MenuVo(long id, String name, String code, String url, int ord) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.url = url;
		this.ord = ord;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public Set<MenuVo> getChildren() {
		return children;
	}

	public void setChildren(Set<MenuVo> children) {
		this.children = children;
	}
	
	
	
}
