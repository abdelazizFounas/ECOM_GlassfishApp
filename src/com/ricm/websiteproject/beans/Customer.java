package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Customer {
	@ApiModelProperty(required = true, value = "Firstname of the customer.")
	private String lastname;
	@ApiModelProperty(required = true, value = "Lastname of the customer.")
	private String name;

	public boolean checkCustomerFilled() {
		return name != null && lastname != null;
	}

	public String getLastname() {
		return lastname;
	}

	public String getName() {
		return name;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setName(String name) {
		this.name = name;
	}
}
