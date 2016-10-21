package com.ricm.websiteproject.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ricm.websiteproject.beans.ArrayVoitures;
import com.ricm.websiteproject.beans.Voiture;
import com.ricm.websiteproject.logic.StaticClass;

@Path("/voitures")
@Api(value = "/voitures")
public class VoitureRest {

	@POST
	@Path("/add")
	@ApiOperation(value = "Returns if yes or not the new vehicule has been registered.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful registration of new vehicule."),
			@ApiResponse(code = 400, message = "The vehicule is not well filled."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTaxi(Voiture voiture) {
		if (voiture.checkVoitureFilled()) {
			StaticClass.alVoitures.add(voiture);
			return Response.ok().build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("/{id}")
	@ApiOperation(value = "Returns the vehicule registered in the application with the wanted Id.", response = Voiture.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval of the vehicule by Id.", response = Voiture.class),
			@ApiResponse(code = 404, message = "Vehicule not found with Id."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTaxiById(@PathParam("id") String id) {
		for (int i = 0; i < StaticClass.alVoitures.size(); i++) {
			if (StaticClass.alVoitures.get(i).getId().equals(id)) {
				return Response.ok(StaticClass.alVoitures.get(i)).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/all")
	@ApiOperation(value = "Returns the list of all the vehicules registered in the application.", response = ArrayVoitures.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval of the vehicules list.", response = ArrayVoitures.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayVoitures getTaxis() {
		ArrayVoitures res = new ArrayVoitures();

		res.setArrayVoitures(StaticClass.alVoitures.toArray(new Voiture[0]));

		return res;
	}
}