package com.yitongyin.modules.ad.form;

import java.util.ArrayList;
import java.util.List;

public class Module implements Cloneable{
	
	private String id;
	
	private String name;
	
	private Integer order;
	
	private String url;
	
	private List<Module> children;
	
	private List<Module> funtions;
	
	private List<Param> params;
	
	private String parentId;
	
	

	@Override
	public int hashCode() {
		if(null == id)return 0;
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Module))return false;
		if(null == id)return false;
		return id.equals(((Module)obj).getId());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder() {
		if(null == order){
			return Integer.MAX_VALUE;
		}
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Module> getChildren() {
		return children;
	}

	public void setChildren(List<Module> children) {
		this.children = children;
	}

	public List<Module> getFuntions() {
		return funtions;
	}

	public void setFuntions(List<Module> funtions) {
		this.funtions = funtions;
	}

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}
	
	

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Module module = new Module();
		module.setChildren(new ArrayList<Module>());
		module.setFuntions(new ArrayList<Module>());
		module.setParams(new ArrayList<Param>());
		module.setId(id);
		module.setName(name);
		module.setOrder(order);
		module.setUrl(url);
		module.setParentId(parentId);
		return module;
	}
	
	

}