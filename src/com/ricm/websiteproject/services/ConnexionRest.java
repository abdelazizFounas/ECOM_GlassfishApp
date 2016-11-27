package com.ricm.websiteproject.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.annotation.Timed;
import com.ricm.websiteproject.beans.AccountInfo;
import com.ricm.websiteproject.beans.Customer;
import com.ricm.websiteproject.beans.NewAccountInfo;

import ejb.Connexion;

@Path("/connexion")
@Api(value = "/connexion")
@RequestScoped
public class ConnexionRest {

	@Inject
	private Connexion connexion;

	@POST
	@Path("/connexion")
	@Timed(name = "connexion")
	@ApiOperation(value = "Returns if yes or not the connexion has been done.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Successful connexion."),
			@ApiResponse(code = 404, message = "Bad connexion."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Connexion(AccountInfo ai,
			@Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);
		System.out.println("CONNEXION DE : " + ai.getMail() + " et : "
				+ ai.getPwd());
		Random r = new Random(System.currentTimeMillis());

		if (connexion.getConnected()) {
			System.out.println("IS CONNECTED");
		}

		if (!connexion.getConnected()) {
			System.out.println("IS NOT CONNECTED");
		}

		if (r.nextBoolean()) {
			connexion.connect(ai.getMail(), ai.getPwd());
			return Response.ok(new Customer()).status(Status.ACCEPTED).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/newAccount")
	@Timed(name = "newAccount")
	@ApiOperation(value = "Returns if yes or not the new account has been registered.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Successful registration of the new account."),
			@ApiResponse(code = 404, message = "Bad connexion."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newAccount(NewAccountInfo nai) {
		Random r = new Random(System.currentTimeMillis());

		if (r.nextBoolean()) {
			connexion.connect(nai.getMail(), nai.getPwd());
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}