package io.swagger.api;


import io.swagger.api.model.Transaction;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:54:24.517Z")
public abstract class PayApiService {
      public abstract Response performTransaction(Transaction body,SecurityContext securityContext)
      throws NotFoundException;
}
