package io.swagger.api.factories;

import gen.io.swagger.api.PayApiService;
import io.swagger.api.impl.PayApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T12:27:35.204Z")
public class PayApiServiceFactory {

   private final static PayApiService service = new PayApiServiceImpl();

   public static PayApiService getPayApi()
   {
      return service;
   }
}
