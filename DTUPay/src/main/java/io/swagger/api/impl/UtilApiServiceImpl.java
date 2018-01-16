package io.swagger.api.impl;

import io.swagger.api.*;


import io.swagger.api.NotFoundException;
import core.persistence.BarcodeStore;
import core.persistence.CustomerStore;
import core.persistence.MerchantStore;
import core.persistence.UserStore;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-15T13:52:27.201Z")
public class UtilApiServiceImpl extends UtilApiService {
      @Override
      public Response flushData(SecurityContext securityContext) throws NotFoundException {
          MerchantStore.getInstance().clearStore();
          CustomerStore.getInstance().clearStore();
          UserStore.getInstance().clearStore();
          BarcodeStore.getInstance().clearStore();
      return Response.ok().build();
  }
}
