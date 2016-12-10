package com.ricm.websiteproject.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;

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
import com.ricm.websiteproject.beans.ArrayPredefinedJourney;
import com.ricm.websiteproject.beans.CarpoolingInfo;
import com.ricm.websiteproject.beans.CarpoolingReservationInfo;
import com.ricm.websiteproject.beans.TaxiInfo;
import com.ricm.websiteproject.beans.TaxiReservationInfo;
import com.ricm.websiteproject.beans.TaxiResult;

import ecom.entities.PredefinedJourney;
import ecom.session.Connexion;
import ecom.session.WayFinder;
import ecom.session.WayReservation;

@Path("/way")
@Api(value = "/way")
public class WayRest {

	@EJB
	private WayFinder wayFinder = new WayFinder();

	@EJB
	private WayReservation wayReservation = new WayReservation();

	@POST
	@Path("/getCarpoolingWays")
	@Timed(name = "getCarpoolingWays")
	@ApiOperation(value = "Returns if yes or not the connexion has been done.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Successful connexion."),
			@ApiResponse(code = 404, message = "Bad connexion."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCarpoolingWays(CarpoolingInfo ci,
			@Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);

		Connexion co = (Connexion) hs.getAttribute("connexion");

		if (co != null && co.getConnected()) {
			System.out.println(ci.toString());

			ArrayList<PredefinedJourney> alp = wayFinder.WayFinderCovoiturage(
					ci.getLatdu(), ci.getLondu(), ci.getLatau(), ci.getLonau(),
					ci.getDeparture());

			System.out.println("LONGUEUR RESULTAT : " + alp.size());

			return Response.ok(new ArrayPredefinedJourney(alp))
					.status(Status.ACCEPTED).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/reserveCarpoolingWay")
	@Timed(name = "reserveCarpoolingWay")
	@ApiOperation(value = "Returns if yes or not the connexion has been done.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Successful connexion."),
			@ApiResponse(code = 404, message = "Bad connexion."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserveCarpoolingWay(CarpoolingReservationInfo cri,
			@Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);

		Connexion co = (Connexion) hs.getAttribute("connexion");

		if (co != null && co.getConnected()) {
			boolean res = wayReservation.WayReservationCovoiturage(
					cri.getPredefinedJourneyId(), co.getUser().getId(),
					cri.getDeparture());

			System.out.println("RESERVATION COVOITURAGE DONE : " + res);

			return Response.ok(new TaxiResult(res)).status(Status.ACCEPTED)
					.build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/reserveTaxi")
	@Timed(name = "reserveTaxi")
	@ApiOperation(value = "Returns if yes or not the new account has been registered.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deconnexion") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserveTaxi(TaxiReservationInfo ti,
			@Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);

		Connexion co = (Connexion) hs.getAttribute("connexion");

		if (co != null && co.getConnected()) {
			boolean res = wayReservation.WayReservationTaxi(co.getUser()
					.getId(), ti.getDepartureCity(), ti.getDepartureLocation(),
					ti.getArrivalCity(), ti.getArrivalLocation(), ti
							.getDepartureDateTime(), ti.getDuration(), ti
							.getPrice());

			System.out.println("RESERVATION TAXI DONE : " + res);

			return Response.ok(new TaxiResult(res)).status(Status.ACCEPTED)
					.build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/taxiAvailable")
	@Timed(name = "taxiAvailable")
	@ApiOperation(value = "Returns if yes or not the new account has been registered.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deconnexion") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response taxiAvailable(TaxiInfo ti,
			@Context HttpServletRequest request) {
		HttpSession hs = request.getSession(true);

		Connexion co = (Connexion) hs.getAttribute("connexion");

		if (co != null && co.getConnected()) {
			boolean res = wayFinder.WayFinderTaxi(ti.getDeparture(),
					ti.getDuration());

			System.out.println("TROUVE : " + res);

			return Response.ok(new TaxiResult(res)).status(Status.ACCEPTED)
					.build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}