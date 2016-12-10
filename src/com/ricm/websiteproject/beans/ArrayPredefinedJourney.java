package com.ricm.websiteproject.beans;

import java.util.ArrayList;

import ecom.entities.PredefinedJourney;

public class ArrayPredefinedJourney {
	private PredefinedJourneyBean[] arrayPredefinedJourneyBean;

	public ArrayPredefinedJourney() {
		this.arrayPredefinedJourneyBean = null;
	}

	public ArrayPredefinedJourney(
			ArrayList<PredefinedJourney> listPredefinedJourney) {
		this.arrayPredefinedJourneyBean = new PredefinedJourneyBean[listPredefinedJourney
				.size()];

		for (int i = 0; i < listPredefinedJourney.size(); i++) {
			this.arrayPredefinedJourneyBean[i] = new PredefinedJourneyBean(
					listPredefinedJourney.get(i));
		}
	}

	public PredefinedJourneyBean[] getArrayPredefinedJourneyBean() {
		return arrayPredefinedJourneyBean;
	}

	public void setArrayPredefinedJourneyBean(
			PredefinedJourneyBean[] arrayPredefinedJourneyBean) {
		this.arrayPredefinedJourneyBean = arrayPredefinedJourneyBean;
	}
}
