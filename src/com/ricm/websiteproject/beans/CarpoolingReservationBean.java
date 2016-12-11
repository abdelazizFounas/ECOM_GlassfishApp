package com.ricm.websiteproject.beans;

import java.util.Date;

import ecom.entities.CarpoolingReservation;

public class CarpoolingReservationBean {
	private String arrivalCity;

	private String arrivalLocation;

	private Date dateReserved;

	private Date day;

	private String departureCity;

	private String departureLocation;

	private String departureTime;

	private String duration;

	private float price;

	public CarpoolingReservationBean() {

	}

	public CarpoolingReservationBean(CarpoolingReservation cr) {
		this.arrivalCity = cr.getJourney().getArrivalCity();
		this.arrivalLocation = cr.getJourney().getArrivalLocation();
		this.departureCity = cr.getJourney().getDepartureCity();
		this.departureLocation = cr.getJourney().getDepartureLocation();
		this.departureTime = cr.getJourney().getDepartureTime();
		this.duration = cr.getJourney().getDuration();
		this.price = cr.getJourney().getPrice();
		this.dateReserved = cr.getDateReserved();
		this.day = cr.getDay();
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}

	public Date getDateReserved() {
		return dateReserved;
	}

	public Date getDay() {
		return day;
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

	public float getPrice() {
		return price;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}

	public void setDateReserved(Date dateReserved) {
		this.dateReserved = dateReserved;
	}

	public void setDay(Date day) {
		this.day = day;
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

	public void setPrice(float price) {
		this.price = price;
	}
}
