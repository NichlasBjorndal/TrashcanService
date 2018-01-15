package io.swagger.api.factories;

import io.swagger.api.BarcodeApiService;
import io.swagger.api.impl.BarcodeApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-15T13:52:27.201Z")
public class BarcodeApiServiceFactory {

   private final static BarcodeApiService service = new BarcodeApiServiceImpl();

   public static BarcodeApiService getBarcodeApi()
   {
      return service;
   }
}
