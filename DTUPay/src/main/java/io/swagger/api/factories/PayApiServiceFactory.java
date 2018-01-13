package io.swagger.api.factories;

import io.swagger.api.PayApiService;
import io.swagger.api.impl.PayApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class PayApiServiceFactory {

   private final static PayApiService service = new PayApiServiceImpl();

   public static PayApiService getPayApi()
   {
      return service;
   }
}
