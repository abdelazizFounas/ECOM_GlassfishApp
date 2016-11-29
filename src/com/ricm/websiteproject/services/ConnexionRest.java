package com.ricm.websiteproject.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ejb.EJB;
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

import ecom.entities.User;
import ecom.session.ConnexionBean;

@Path("/connexion")
@Api(value = "/connexion")
public class ConnexionRest {

	@EJB
	private ConnexionBean connexionBean = new ConnexionBean();

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

		User u = connexionBean.ConnectClient(ai.getMail(), ai.getPwd());

		if (u != null) {
			System.out.println("IS CONNECTED");
			hs.setAttribute("email", ai.getMail());
			hs.setAttribute("pwd", ai.getPwd());
			u.setPasswdHash("");
			u.setCarpoolingReservations(null);
			u.setTaxiReservations(null);
			return Response.ok(u).status(Status.ACCEPTED).build();
		} else {
			System.out.println("IS NOT CONNECTED");
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
	public Response newAccount(User u, @Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);
		User ures = connexionBean.CreateUser(u.getAddress(), u.getFirstName(),
				u.getLastName(), u.getBirthDate(), u.getCity(), u.getCountry(),
				u.getEmail(), u.getPasswdHash(), u.getPhone(), u.getZip());
		if (ures != null) {
			System.out.println("IS NEW CONNECTED");
			hs.setAttribute("email", u.getEmail());
			hs.setAttribute("pwd", u.getPasswdHash());
			ures.setPasswdHash("");
			ures.setCarpoolingReservations(null);
			ures.setTaxiReservations(null);
			return Response.ok(ures).status(Status.ACCEPTED).build();
		} else {
			System.out.println("IS NOT NEW CONNECTED");
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}