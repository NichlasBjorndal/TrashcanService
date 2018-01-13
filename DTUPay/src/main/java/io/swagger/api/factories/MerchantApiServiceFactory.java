package io.swagger.api.factories;

import io.swagger.api.MerchantApiService;
import io.swagger.api.impl.MerchantApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class MerchantApiServiceFactory {

   private final static MerchantApiService service = new MerchantApiServiceImpl();

   public static MerchantApiService getMerchantApi()
   {
      return service;
   }
}
