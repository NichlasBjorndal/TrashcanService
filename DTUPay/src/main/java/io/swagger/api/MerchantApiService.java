package io.swagger.api;


import io.swagger.api.model.Merchant;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:54:24.517Z")
public abstract class MerchantApiService {
      public abstract Response createMerchant(Merchant body,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response getMerchantByCVR(String cvr,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response merchantCvrDelete(String cvr,SecurityContext securityContext)
      throws NotFoundException;
}
