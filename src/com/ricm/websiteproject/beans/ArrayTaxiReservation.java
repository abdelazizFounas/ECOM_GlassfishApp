package com.ricm.websiteproject.beans;

import java.util.List;

import ecom.entities.TaxiReservation;

public class ArrayTaxiReservation {
	private TaxiReservationBean[] arrayTaxiReservationBean;

	public ArrayTaxiReservation() {
		this.arrayTaxiReservationBean = null;
	}

	public ArrayTaxiReservation(List<TaxiReservation> listTaxiReservation) {
		this.arrayTaxiReservationBean = new TaxiReservationBean[listTaxiReservation
				.size()];

		for (int i = 0; i < listTaxiReservation.size(); i++) {
			this.arrayTaxiReservationBean[i] = new TaxiReservationBean(
					listTaxiReservation.get(i));
		}
	}

	public TaxiReservationBean[] getArrayTaxiReservationBean() {
		return arrayTaxiReservationBean;
	}

	public void setArrayTaxiReservationBean(
			TaxiReservationBean[] arrayTaxiReservationBean) {
		this.arrayTaxiReservationBean = arrayTaxiReservationBean;
	}
}
