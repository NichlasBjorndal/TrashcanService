package io.swagger.api.factories;

import gen.io.swagger.api.CustomerApiService;
import io.swagger.api.impl.CustomerApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T12:27:35.204Z")
public class CustomerApiServiceFactory {

   private final static CustomerApiService service = new CustomerApiServiceImpl();

   public static CustomerApiService getCustomerApi()
   {
      return service;
   }
}
