package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class CarpoolingInfo {
	@ApiModelProperty(required = true, value = "Departure date of the user.")
	private Date departure;
	@ApiModelProperty(required = true, value = "Latitude of arrival.")
	private float latau;
	@ApiModelProperty(required = true, value = "Latitude of departure.")
	private float latdu;
	@ApiModelProperty(required = true, value = "Longitude of arrival.")
	private float lonau;
	@ApiModelProperty(required = true, value = "Longitude of departure.")
	private float londu;

	public Date getDeparture() {
		return departure;
	}

	public float getLatau() {
		return latau;
	}

	public float getLatdu() {
		return latdu;
	}

	public float getLonau() {
		return lonau;
	}

	public float getLondu() {
		return londu;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public void setLatau(float latau) {
		this.latau = latau;
	}

	public void setLatdu(float latdu) {
		this.latdu = latdu;
	}

	public void setLonau(float lonau) {
		this.lonau = lonau;
	}

	public void setLondu(float londu) {
		this.londu = londu;
	}

	@Override
	public String toString() {
		return "Lat departure : " + latdu + " Lon departure : " + londu
				+ " Lat arrival : " + latau + " Lon arrival : " + lonau;
	}
}
