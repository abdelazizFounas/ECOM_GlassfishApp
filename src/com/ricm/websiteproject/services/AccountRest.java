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
import com.ricm.websiteproject.beans.InfoBean;
import com.ricm.websiteproject.beans.TaxiResult;

import ecom.entities.User;
import ecom.session.AccountBean;
import ecom.session.Connexion;

@Path("/account")
@Api(value = "/account")
public class AccountRest {

	@EJB
	private AccountBean accountBean = new AccountBean();

	@POST
	@Path("/changeMail")
	@Timed(name = "changeMail")
	@ApiOperation(value = "Returns if yes or not the connexion has been done.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Successful connexion."),
			@ApiResponse(code = 404, message = "Bad connexion."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeMail(InfoBean ib, @Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);

		Connexion co = (Connexion) hs.getAttribute("connexion");

		if (co != null && co.getConnected()) {
			User u = accountBean.changeMail(co.getUser().getId(),
					ib.getInfobean(), ib.getPassword());

			if (u != null) {
				co.setUser(u);
				return Response.ok(new TaxiResult(true))
						.status(Status.ACCEPTED).build();
			} else {
				return Response.ok(new TaxiResult(false))
						.status(Status.ACCEPTED).build();
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/changePassword")
	@Timed(name = "changePassword")
	@ApiOperation(value = "Returns if yes or not the connexion has been done.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Successful connexion."),
			@ApiResponse(code = 404, message = "Bad connexion."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePassword(InfoBean ib,
			@Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);

		Connexion co = (Connexion) hs.getAttribute("connexion");

		if (co != null && co.getConnected()) {
			User u = accountBean.changePassword(co.getUser().getId(),
					ib.getInfobean(), ib.getPassword());

			if (u != null) {
				co.setUser(u);
				return Response.ok(new TaxiResult(true))
						.status(Status.ACCEPTED).build();
			} else {
				return Response.ok(new TaxiResult(false))
						.status(Status.ACCEPTED).build();
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/changePhone")
	@Timed(name = "changePhone")
	@ApiOperation(value = "Returns if yes or not the connexion has been done.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Successful connexion."),
			@ApiResponse(code = 404, message = "Bad connexion."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePhone(InfoBean ib, @Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);

		Connexion co = (Connexion) hs.getAttribute("connexion");

		if (co != null && co.getConnected()) {
			User u = accountBean.changePhone(co.getUser().getId(),
					ib.getInfobean(), ib.getPassword());

			if (u != null) {
				co.setUser(u);
				return Response.ok(new TaxiResult(true))
						.status(Status.ACCEPTED).build();
			} else {
				return Response.ok(new TaxiResult(false))
						.status(Status.ACCEPTED).build();
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}