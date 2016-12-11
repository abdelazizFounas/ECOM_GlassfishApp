package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;

@ApiModel
public class InfoBean {
	private String infobean;

	private String password;

	public InfoBean() {
	}

	public String getInfobean() {
		return infobean;
	}

	public String getPassword() {
		return password;
	}

	public void setInfobean(String infobean) {
		this.infobean = infobean;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
