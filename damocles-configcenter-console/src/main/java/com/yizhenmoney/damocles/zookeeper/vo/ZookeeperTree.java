package com.yizhenmoney.damocles.zookeeper.vo;

import java.util.List;

import com.google.common.collect.Lists;

public class ZookeeperTree {
	private Long id;
	private String name;
	private String functionSum;
	private String functionCode;
	private ZookeeperTree parent;
	private String url;
	private String nodeUrl;
	private List<ZookeeperTree> children = Lists.newArrayList();
	public ZookeeperTree(Long id,String name,String functionSum,String functionCode,String url){
		this.id=id;
		this.name=name;
		this.functionSum=functionSum;
		this.functionCode=functionCode;
		this.url=url;
	}
	
	public String getNodeUrl() {
		return nodeUrl;
	}

	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}

	public ZookeeperTree(){
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFunctionSum() {
		return functionSum;
	}
	public void setFunctionSum(String functionSum) {
		this.functionSum = functionSum;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public ZookeeperTree getParent() {
		return parent;
	}
	public void setParent(ZookeeperTree parent) {
		this.parent = parent;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<ZookeeperTree> getChildren() {
		return children;
	}
	public void setChildren(List<ZookeeperTree> children) {
		this.children = children;
	}
	
}
