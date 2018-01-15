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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-15T13:54:39.165Z")
public class CustomerApi  {
   private final CustomerApiService delegate = CustomerApiServiceFactory.getCustomerApi();

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create a new customer in DTUPay", notes = "", response = String.class, tags={ "customer", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "New customer created", response = String.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Customer already exist in DTUPay", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Customer doesn't have bank account", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
    public Response createCustomer(@ApiParam(value = "Customer object that needs to be added to the DTUPay" ,required=true) Customer body,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createCustomer(body,securityContext);
    }
}
