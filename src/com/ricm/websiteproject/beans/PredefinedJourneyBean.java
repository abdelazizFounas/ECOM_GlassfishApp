package com.ricm.websiteproject.beans;

import java.util.Date;

import ecom.entities.PredefinedJourney;

public class PredefinedJourneyBean {
	private String arrivalCity;

	private String arrivalLocation;

	private String days;

	private String departureCity;

	private String departureLocation;

	private String departureTime;

	private String duration;

	private int id;

	private float price;

	private Date serviceEnd;

	public PredefinedJourneyBean() {

	}

	public PredefinedJourneyBean(PredefinedJourney pf) {
		this.arrivalCity = pf.getArrivalCity();
		this.arrivalLocation = pf.getArrivalLocation();
		this.days = pf.getDays();
		this.departureCity = pf.getDepartureCity();
		this.departureLocation = pf.getDepartureLocation();
		this.departureTime = pf.getDepartureTime();
		this.duration = pf.getDuration();
		this.id = pf.getId();
		this.price = pf.getPrice();
		this.serviceEnd = pf.getServiceEnd();
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}

	public String getDays() {
		return days;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public String getDepartureLocation() {
		return departureLocation;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public String getDuration() {
		return duration;
	}

	public int getId() {
		return id;
	}

	public float getPrice() {
		return price;
	}

	public Date getServiceEnd() {
		return serviceEnd;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setServiceEnd(Date serviceEnd) {
		this.serviceEnd = serviceEnd;
	}

}
