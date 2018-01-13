package gen.io.swagger.api;

import gen.io.swagger.model.Transaction;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T12:27:35.204Z")
public abstract class PayApiService {
      public abstract Response performTransaction(Transaction body,SecurityContext securityContext)
      throws NotFoundException;
}
