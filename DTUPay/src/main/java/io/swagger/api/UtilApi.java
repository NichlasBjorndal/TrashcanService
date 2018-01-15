package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.UtilApiService;
import io.swagger.api.factories.UtilApiServiceFactory;

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

@Path("/util")


@io.swagger.annotations.Api(description = "the util API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-15T13:36:32.917Z")
public class UtilApi  {
   private final UtilApiService delegate = UtilApiServiceFactory.getUtilApi();

    @POST
    @Path("/flush")
    
    
    @io.swagger.annotations.ApiOperation(value = "flush the data in DTUPay", notes = "", response = Void.class, tags={ "util", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Data successfully flushed", response = Void.class) })
    public Response flushData(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.flushData(securityContext);
    }
}
