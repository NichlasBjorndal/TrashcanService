package io.swagger.api.impl;

import io.swagger.api.*;


import io.swagger.model.Transaction;

import io.swagger.api.NotFoundException;

import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Stateless
@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/FastMoneyBankTransactionMDB",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "FastMoneyBankTransactionQueue"
                )}
)
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class PayApiServiceImpl extends PayApiService {
      @Override
      public Response performTransaction(Transaction body,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
}