package io.swagger.api;

import io.swagger.api.factories.MerchantApiServiceFactory;

import io.swagger.annotations.ApiParam;

import io.swagger.api.model.Merchant;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/merchant")


@io.swagger.annotations.Api(description = "the merchant API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:54:24.517Z")
public class MerchantApi  {
   private final MerchantApiService delegate = MerchantApiServiceFactory.getMerchantApi();
 @POST

 @Consumes({ "application/json" })
 @Produces({ "application/json" })
 @io.swagger.annotations.ApiOperation(value = "Create a new merchant in DTUPay", notes = "", response = Void.class, tags={ "merchant", })
 @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 201, message = "New merchant created", response = Void.class),

         @io.swagger.annotations.ApiResponse(code = 400, message = "An account already exists", response = Void.class),

         @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
 public Response createMerchant(@ApiParam(value = "Merchant object that needs to be added to DTUPay" ,required=true) Merchant body,@Context SecurityContext securityContext)
         throws NotFoundException {
  return delegate.createMerchant(body,securityContext);
 }
    @GET
    @Path("/{cvr}")

    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find merchant by CVR", notes = "Returns a single merchant", response = Merchant.class, tags={ "merchant", })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Merchant.class),

        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid CVR supplied", response = Void.class),

        @io.swagger.annotations.ApiResponse(code = 404, message = "Merchant not found", response = Void.class) })
    public Response getMerchantByCVR( @PathParam("cvr") String cvr,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getMerchantByCVR(cvr,securityContext);
    }
    @DELETE
    @Path("/{cvr}")

    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete merchant by CVR", notes = "", response = Void.class, tags={ "merchant", })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid CVR supplied", response = Void.class),

        @io.swagger.annotations.ApiResponse(code = 404, message = "Merchant not found", response = Void.class) })
    public Response merchantCvrDelete( @PathParam("cvr") String cvr,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.merchantCvrDelete(cvr,securityContext);
    }
}
