package com.ricm.websiteproject.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.annotation.Timed;
import com.ricm.websiteproject.beans.ArrayCarpoolingReservation;
import com.ricm.websiteproject.beans.ArrayTaxiReservation;

import ecom.entities.CarpoolingReservation;
import ecom.entities.TaxiReservation;
import ecom.session.Connexion;
import ecom.session.ReservationFinder;

@Path("/reservation")
@Api(value = "/reservation")
public class ReservationRest {

	@EJB
	private ReservationFinder reservationFinder = new ReservationFinder();

	@GET
	@Path("/getCarpoolingReservation")
	@Timed(name = "getCarpoolingReservation")
	@ApiOperation(value = "Returns if yes or not the connexion has been done.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Successful connexion."),
			@ApiResponse(code = 404, message = "Bad connexion."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCarpoolingReservation(@Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);

		Connexion co = (Connexion) hs.getAttribute("connexion");

		if (co != null && co.getConnected()) {
			List<CarpoolingReservation> acpr = reservationFinder
					.getCarpoolingReservation(co.getUser().getId());

			return Response.ok(new ArrayCarpoolingReservation(acpr))
					.status(Status.ACCEPTED).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/getCarpoolingTaxi")
	@Timed(name = "getCarpoolingTaxi")
	@ApiOperation(value = "Returns if yes or not the connexion has been done.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Successful connexion."),
			@ApiResponse(code = 404, message = "Bad connexion."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCarpoolingTaxi(@Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);

		Connexion co = (Connexion) hs.getAttribute("connexion");

		if (co != null && co.getConnected()) {
			List<TaxiReservation> acpr = reservationFinder
					.getTaxiReservation(co.getUser().getId());

			return Response.ok(new ArrayTaxiReservation(acpr))
					.status(Status.ACCEPTED).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}