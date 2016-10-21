package com.ricm.websiteproject.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Voiture {
	@ApiModelProperty(required = true, value = "Category of the vehicule.")
	private int category;
	@ApiModelProperty(required = true, value = "Id string of the vehicule.")
	private String id;
	@ApiModelProperty(required = true, value = "Place where the vehicule is currently.")
	private String lieu;
	@ApiModelProperty(required = true, value = "Number of places inside the vehicule.")
	private int nbPlaces;

	public boolean checkVoitureFilled() {
		return id != null && lieu != null;
	}

	public int getCategory() {
		return category;
	}

	public String getId() {
		return id;
	}

	public String getLieu() {
		return lieu;
	}

	public int getNbPlaces() {
		return nbPlaces;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public void setNbPlaces(int nbPlaces) {
		this.nbPlaces = nbPlaces;
	}
}
