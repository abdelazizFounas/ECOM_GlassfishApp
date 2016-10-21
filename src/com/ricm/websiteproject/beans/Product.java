package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Product {
	private int id;
	private String name;

	@ApiModelProperty(position = 1, required = true, value = "username containing only lowercase letters or numbers")
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
