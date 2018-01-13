package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.CustomerApiService;
import io.swagger.api.factories.CustomerApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Customer;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/customer")


@io.swagger.annotations.Api(description = "the customer API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class CustomerApi  {
   private final CustomerApiService delegate = CustomerApiServiceFactory.getCustomerApi();

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create a new customer in DTUPay", notes = "", response = Void.class, tags={ "customer", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "New customer created", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
    public Response createCustomer(@ApiParam(value = "Customer object that needs to be added to the DTUPay" ,required=true) Customer body,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createCustomer(body,securityContext);
    }
    @DELETE
    @Path("/{cpr}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete customer by CPR", notes = "", response = Void.class, tags={ "customer", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid CPR supplied", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Customer not found", response = Void.class) })
    public Response deleteCustomerByCPR( @PathParam("cpr") String cpr,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.deleteCustomerByCPR(cpr,securityContext);
    }
    @GET
    @Path("/{cpr}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find customer by CPR", notes = "Returns a single customer", response = Customer.class, tags={ "customer", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Customer.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid CPR supplied", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Customer not found", response = Void.class) })
    public Response getCustomerByCPR( @PathParam("cpr") String cpr,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getCustomerByCPR(cpr,securityContext);
    }
}
