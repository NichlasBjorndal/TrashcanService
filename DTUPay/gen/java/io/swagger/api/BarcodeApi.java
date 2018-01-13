package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.BarcodeApiService;
import io.swagger.api.factories.BarcodeApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;


import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/barcode")


@io.swagger.annotations.Api(description = "the barcode API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class BarcodeApi  {
   private final BarcodeApiService delegate = BarcodeApiServiceFactory.getBarcodeApi();

    @GET
    @Path("/{cpr}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get barcode for customer specified by CPR", notes = "", response = String.class, tags={ "barcode", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = String.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
    public Response getCustomerBarcode( @PathParam("cpr") String cpr,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getCustomerBarcode(cpr,securityContext);
    }
}
