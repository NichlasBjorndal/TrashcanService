package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.Transaction;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-15T13:54:39.165Z")
public abstract class PayApiService {
      /**
       * @param body containing the transaction information
       * @param securityContext
       * @return response from transaction
       * @throws NotFoundException
       */
      public abstract Response performTransaction(Transaction body,SecurityContext securityContext)
      throws NotFoundException;
}
