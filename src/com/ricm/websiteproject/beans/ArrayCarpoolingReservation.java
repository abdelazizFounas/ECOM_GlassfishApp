package com.ricm.websiteproject.beans;

import java.util.List;

import ecom.entities.CarpoolingReservation;

public class ArrayCarpoolingReservation {
	private CarpoolingReservationBean[] arrayCarpoolingReservationBean;

	public ArrayCarpoolingReservation() {
		this.arrayCarpoolingReservationBean = null;
	}

	public ArrayCarpoolingReservation(
			List<CarpoolingReservation> listCarpoolingReservation) {
		this.arrayCarpoolingReservationBean = new CarpoolingReservationBean[listCarpoolingReservation
				.size()];

		for (int i = 0; i < listCarpoolingReservation.size(); i++) {
			this.arrayCarpoolingReservationBean[i] = new CarpoolingReservationBean(
					listCarpoolingReservation.get(i));
		}
	}

	public CarpoolingReservationBean[] getArrayCarpoolingReservationBean() {
		return arrayCarpoolingReservationBean;
	}

	public void setArrayCarpoolingReservationBean(
			CarpoolingReservationBean[] arrayCarpoolingReservationBean) {
		this.arrayCarpoolingReservationBean = arrayCarpoolingReservationBean;
	}
}
