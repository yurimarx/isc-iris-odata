package com.intersystems.iris.odata.model;

public class SQLElement {

	private String name;
	private String type;
	
	public SQLElement() {
	
	}
	
	public SQLElement(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
}
