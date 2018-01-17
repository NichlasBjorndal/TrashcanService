package io.swagger.api.factories;

import io.swagger.api.MerchantApiService;
import io.swagger.api.impl.MerchantApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-17T18:47:18.042Z")
public class MerchantApiServiceFactory {

   private final static MerchantApiService service = new MerchantApiServiceImpl();

   public static MerchantApiService getMerchantApi()
   {
      return service;
   }
}
