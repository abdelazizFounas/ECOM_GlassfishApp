package com.ricm.websiteproject.beans;

import java.util.Date;

import ecom.entities.TaxiReservation;

public class TaxiReservationBean {
	private String arrivalCity;

	private String arrivalLocation;

	private Date dateReserved;

	private String departureCity;

	private Date departureDateTime;

	private String departureLocation;

	private String duration;

	private float price;

	public TaxiReservationBean() {

	}

	public TaxiReservationBean(TaxiReservation tr) {
		this.arrivalCity = tr.getArrivalCity();
		this.arrivalLocation = tr.getArrivalLocation();
		this.departureCity = tr.getDepartureCity();
		this.departureLocation = tr.getDepartureLocation();
		this.duration = tr.getDuration();
		this.price = tr.getPrice();
		this.dateReserved = tr.getDateReserved();
		this.departureDateTime = tr.getDepartureDateTime();
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

	public void setDateReserved(Date dateReserved) {
		this.dateReserved = dateReserved;
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
