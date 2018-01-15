package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.MerchantApiService;
import io.swagger.api.factories.MerchantApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Merchant;

import java.util.Map;
import java.util.List;

import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/merchant")


@io.swagger.annotations.Api(description = "the merchant API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-15T13:54:39.165Z")
public class MerchantApi {
    private final MerchantApiService delegate = MerchantApiServiceFactory.getMerchantApi();

    @POST

    @Consumes({"application/json"})
    @Produces({"application/json"})
    @io.swagger.annotations.ApiOperation(value = "Create a new merchant in DTUPay", notes = "", response = String.class, tags = {"merchant",})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 201, message = "New merchant created", response = String.class),

            @io.swagger.annotations.ApiResponse(code = 400, message = "Merchant already exist in DTUPay", response = Void.class),

            @io.swagger.annotations.ApiResponse(code = 403, message = "Merchant doesn't have bank account", response = Void.class),

            @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class)})
    public Response createMerchant(@ApiParam(value = "Merchant object that needs to be added to DTUPay", required = true) Merchant body, @Context SecurityContext securityContext)
            throws NotFoundException {
        return delegate.createMerchant(body, securityContext);
    }
}
