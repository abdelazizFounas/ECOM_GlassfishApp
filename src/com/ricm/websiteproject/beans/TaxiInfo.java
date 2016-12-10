package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class TaxiInfo {
	private Date departure;

	private String duration;

	public Date getDeparture() {
		return departure;
	}

	public String getDuration() {
		return duration;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
