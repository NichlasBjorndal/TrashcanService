package io.swagger.api.factories;

import io.swagger.api.CustomerApiService;
import io.swagger.api.impl.CustomerApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-15T13:36:32.917Z")
public class CustomerApiServiceFactory {

   private final static CustomerApiService service = new CustomerApiServiceImpl();

   public static CustomerApiService getCustomerApi()
   {
      return service;
   }
}
