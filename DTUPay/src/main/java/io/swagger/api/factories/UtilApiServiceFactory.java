package io.swagger.api.factories;

import io.swagger.api.UtilApiService;
import io.swagger.api.impl.UtilApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-15T13:52:27.201Z")
public class UtilApiServiceFactory {

   private final static UtilApiService service = new UtilApiServiceImpl();

   public static UtilApiService getUtilApi()
   {
      return service;
   }
}
