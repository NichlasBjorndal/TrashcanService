package gen.io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.api.factories.PayApiServiceFactory;
import gen.io.swagger.api.*;
import gen.io.swagger.model.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/pay")


@io.swagger.annotations.Api(description = "the pay API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T12:27:35.204Z")
public class PayApi  {
   private final PayApiService delegate = PayApiServiceFactory.getPayApi();

    @POST
    
    @Consumes({ "application/json" })
    
    @io.swagger.annotations.ApiOperation(value = "Perform a new transaction between two users", notes = "", response = Void.class, tags={ "pay", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
    public Response performTransaction(@ApiParam(value = "Transaction object" ,required=true) Transaction body,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.performTransaction(body,securityContext);
    }
}
