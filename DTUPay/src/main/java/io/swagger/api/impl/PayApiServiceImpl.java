package io.swagger.api.impl;

import core.utils.GsonWrapper;
import io.swagger.api.*;


import io.swagger.model.Transaction;

import io.swagger.api.NotFoundException;
import jsmprovider.JmsProvider;


import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Stateless
@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/PayMDB",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "PayQueue"
                )}
)

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class PayApiServiceImpl extends PayApiService {

    private static final String PAY_QUEUE = "PayQueue";

      @Override
      public Response performTransaction(Transaction body, SecurityContext securityContext)
      throws NotFoundException {

          JmsProvider jmsProvider = new JmsProvider();

          String response = "";
          try {
              response = jmsProvider.sendMessage(PAY_QUEUE, body);
          } catch (Exception e) {
              return Response.serverError().build();
          }

          String parsedResponse = (String) GsonWrapper.fromJson(response, String.class);

          Response httpRes;
          if (parsedResponse.equals(PayResponse.SUCCESSFUL_PAYMENT.getValue())) {
              httpRes = Response.status(201).entity(PayResponse.SUCCESSFUL_PAYMENT.getValue()).build();
          } else if (parsedResponse.equals(PayResponse.INVALID_INPUT.getValue())) {
              httpRes = Response.status(405).entity(PayResponse.INVALID_INPUT.getValue()).build();
          } else if (parsedResponse.equals(PayResponse.INVALID_BARCODE.getValue())) {
              httpRes = Response.status(400).entity(PayResponse.INVALID_BARCODE.getValue()).build();
          } else  if (parsedResponse.equals(PayResponse.INVALID_MERCHANT.getValue())) {
              httpRes = Response.status(403).entity(PayResponse.INVALID_MERCHANT.getValue()).build();
          } else {
              httpRes = Response.status(403).entity(PayResponse.UNEXPECTED.getValue()).build();
          }
          return httpRes;
  }
}