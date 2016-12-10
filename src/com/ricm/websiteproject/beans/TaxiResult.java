package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;

@ApiModel
public class TaxiResult {
	private boolean taxiPresent;

	public TaxiResult() {
		this.taxiPresent = false;
	}

	public TaxiResult(boolean taxiPresent) {
		this.taxiPresent = taxiPresent;
	}

	public boolean getTaxiPresent() {
		return taxiPresent;
	}

	public void setTaxiPresent(boolean taxiPresent) {
		this.taxiPresent = taxiPresent;
	}
}
