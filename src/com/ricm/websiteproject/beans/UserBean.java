package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;

import java.util.Date;

import ecom.entities.User;

@ApiModel
public class UserBean {
	private Date accountCreationDate;

	private String address;

	private Date birthDate;

	private String city;

	private String country;

	private String email;

	private String firstName;

	private int id;

	private String lastName;

	private String phone;

	private String zip;

	public UserBean() {
	}

	public UserBean(User u) {
		this.accountCreationDate = u.getAccountCreationDate();
		this.address = u.getAddress();
		this.birthDate = u.getBirthDate();
		this.city = u.getCity();
		this.country = u.getCountry();
		this.email = u.getEmail();
		this.firstName = u.getFirstName();
		this.id = u.getId();
		this.lastName = u.getLastName();
		this.phone = u.getPhone();
		this.zip = u.getZip();
	}

	public Date getAccountCreationDate() {
		return this.accountCreationDate;
	}

	public String getAddress() {
		return this.address;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public String getCity() {
		return this.city;
	}

	public String getCountry() {
		return this.country;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public int getId() {
		return this.id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getZip() {
		return this.zip;
	}

	public void setAccountCreationDate(Date accountCreationDate) {
		this.accountCreationDate = accountCreationDate;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}