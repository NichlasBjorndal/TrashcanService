package io.swagger.api;


import io.swagger.api.model.Customer;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:54:24.517Z")
public abstract class CustomerApiService {
      public abstract Response createCustomer(Customer body,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response deleteCustomerByCPR(String cpr,SecurityContext securityContext)
      throws NotFoundException;
      public abstract Response getCustomerByCPR(String cpr,SecurityContext securityContext)
      throws NotFoundException;
}
