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


/**
 * Implementation of the payment API functionality
 */
@Stateless
@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/PayMDB",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "PayQueue"
                ), @JMSDestinationDefinition(
                name = "java:/queue/FastMoneyBankTransactionMDB",
                interfaceName = "javax.jms.Queue",
                destinationName = "FastMoneyBankTransactionQueue"
        )
        }

)

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class PayApiServiceImpl extends PayApiService {

    private static final String PAY_QUEUE = "PayQueue";

    /**Putting the tranaction in the PayQueue. Interpretation of response deciding the response code.
     * @param body containing the transaction information
     * @param securityContext
     * @return transaction response
     * @throws NotFoundException
     */
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

          //Interpret response from payment bean
          Response httpRes;
          if (parsedResponse.equals(PayResponse.NOT_ENOUGH_FUNDS.getValue())) {
              httpRes = Response.status(400).entity(parsedResponse).build();
          } else if (parsedResponse.equals(PayResponse.NO_BANK_ACCOUNT.getValue())) {
              httpRes = Response.status(403).entity(parsedResponse).build();
          } else if (parsedResponse.equals(PayResponse.INVALID_MERCHANT.getValue())) {
              httpRes = Response.status(406).entity(parsedResponse).build();
          } else if (parsedResponse.equals(PayResponse.INVALID_BARCODE.getValue())) {
              httpRes = Response.status(405).entity(parsedResponse).build();
          } else if (parsedResponse.equals(PayResponse.SERVER_ERROR.getValue())) {
              return Response.serverError().build();
          } else {
              httpRes = Response.status(201).entity(PayResponse.SUCCESSFUL_PAYMENT.getValue()).build();
          }
          return httpRes;
  }
}