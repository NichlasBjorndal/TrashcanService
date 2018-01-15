package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;



import java.util.List;
import io.swagger.api.NotFoundException;
import persistence.BarcodeStore;
import persistence.CustomerStore;
import persistence.MerchantStore;
import persistence.UserStore;

import java.io.InputStream;

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
