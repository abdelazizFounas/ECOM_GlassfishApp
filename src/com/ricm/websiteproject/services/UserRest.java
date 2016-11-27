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

import com.ricm.websiteproject.beans.ArrayCustomers;
import com.ricm.websiteproject.beans.Customer;
import com.ricm.websiteproject.logic.StaticClass;

@Path("/customers")
@Api(value = "/customers")
public class UserRest {

	@POST
	@Path("/add")
	@ApiOperation(value = "Returns if yes or not the new customer has been registered.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful registration of new customer."),
			@ApiResponse(code = 400, message = "The customer is not well filled."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTaxi(Customer customer) {
		if (customer.checkCustomerFilled()) {
			StaticClass.alCustomers.add(customer);
			return Response.ok().build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("/{name}")
	@ApiOperation(value = "Returns the customer registered in the application with the wanted name.", response = Customer.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval of the customer by name.", response = Customer.class),
			@ApiResponse(code = 404, message = "Customer not found with name."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerByName(@PathParam("name") String name) {
		for (int i = 0; i < StaticClass.alCustomers.size(); i++) {
			if (StaticClass.alCustomers.get(i).getName().equals(name)) {
				return Response.ok(StaticClass.alCustomers.get(i)).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/all")
	@ApiOperation(value = "Returns the list of all the customers registered in the application.", response = ArrayCustomers.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval of the customers list.", response = ArrayCustomers.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayCustomers getCustomers() {
		ArrayCustomers res = new ArrayCustomers();

		res.setArrayCustomers(StaticClass.alCustomers.toArray(new Customer[0]));

		return res;
	}
}