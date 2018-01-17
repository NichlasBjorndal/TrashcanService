package io.swagger.api.factories;

import io.swagger.api.CustomerApiService;
import io.swagger.api.impl.CustomerApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-17T18:47:18.042Z")
public class CustomerApiServiceFactory {

   private final static CustomerApiService service = new CustomerApiServiceImpl();

   public static CustomerApiService getCustomerApi()
   {
      return service;
   }
}
