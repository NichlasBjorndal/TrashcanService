package io.swagger.api.impl;

import io.swagger.api.*;
import gen.io.swagger.api.*;
import gen.io.swagger.model.*;


import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T12:27:35.204Z")
public class PayApiServiceImpl extends PayApiService {
      @Override
      public Response performTransaction(Transaction body,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
}
