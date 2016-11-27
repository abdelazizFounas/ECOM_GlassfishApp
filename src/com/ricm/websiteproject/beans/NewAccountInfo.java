package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class NewAccountInfo {
	@ApiModelProperty(required = true, value = "Firstname of the customer.")
	private String firstname;
	@ApiModelProperty(required = true, value = "Lastname of the customer.")
	private String lastname;
	@ApiModelProperty(required = true, value = "Login of the account.")
	private String mail;
	@ApiModelProperty(required = true, value = "Password of the account.")
	private String pwd;

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getMail() {
		return mail;
	}

	public String getPwd() {
		return pwd;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
