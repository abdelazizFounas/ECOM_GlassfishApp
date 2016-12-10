package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class TaxiReservationInfo {
	private String arrivalCity;
	private String arrivalLocation;
	private String departureCity;
	private Date departureDateTime;
	private String departureLocation;
	private String duration;
	private float price;

	public String getArrivalCity() {
		return arrivalCity;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public Date getDepartureDateTime() {
		return departureDateTime;
	}

	public String getDepartureLocation() {
		return departureLocation;
	}

	public String getDuration() {
		return duration;
	}

	public float getPrice() {
		return price;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public void setDepartureDateTime(Date departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
