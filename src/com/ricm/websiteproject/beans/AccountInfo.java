package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class AccountInfo {
	@ApiModelProperty(required = true, value = "Login of the account.")
	private String mail;
	@ApiModelProperty(required = true, value = "Password of the account.")
	private String pwd;

	public String getMail() {
		return mail;
	}

	public String getPwd() {
		return pwd;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
