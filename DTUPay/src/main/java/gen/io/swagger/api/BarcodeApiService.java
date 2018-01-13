package gen.io.swagger.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T12:27:35.204Z")
public abstract class BarcodeApiService {
      public abstract Response getCustomerBarcode(String cpr,SecurityContext securityContext)
      throws NotFoundException;
}
