package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class CarpoolingReservationInfo {
	@ApiModelProperty(required = true, value = "Departure date of the user.")
	private Date departure;
	@ApiModelProperty(required = true, value = "Id of the chosen predefined journey.")
	private int predefinedJourneyId;

	public Date getDeparture() {
		return departure;
	}

	public int getPredefinedJourneyId() {
		return predefinedJourneyId;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public void setPredefinedJourneyId(int predefinedJourneyId) {
		this.predefinedJourneyId = predefinedJourneyId;
	}
}
