package io.swagger.api.impl;

import io.swagger.api.*;


import io.swagger.model.Customer;

import io.swagger.api.NotFoundException;
import jsmprovider.JmsProvider;
import core.utils.GsonWrapper;

import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Stateless
@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/CreateCustomerMDB",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "CreateCustomerQueue"
                )}
)

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class CustomerApiServiceImpl extends CustomerApiService {

    private static final String CREATE_CUSTOMER_QUEUE = "CreateCustomerQueue";

    @Override
    public Response createCustomer(Customer body, SecurityContext securityContext) throws NotFoundException {
        JmsProvider jmsProvider = new JmsProvider();

        String response = "";
        try {
            response = jmsProvider.sendMessage(CREATE_CUSTOMER_QUEUE, body);
        } catch (Exception e) {
            return Response.serverError().build();
        }

        String parsedResponse = (String) GsonWrapper.fromJson(response, String.class);

        Response httpRes;
        if (parsedResponse.equals(CustomerResponse.ALREADY_EXISTS.getValue())) {
            httpRes = Response.status(400).entity(parsedResponse).build();
        }
        else if (parsedResponse.equals(CustomerResponse.NO_BANK_ACCOUNT.getValue())) {
            httpRes = Response.status(403).entity(parsedResponse).build();
        }
        else if (parsedResponse.equals(CustomerResponse.INVALID_INPUT.getValue())) {
            httpRes = Response.status(405).entity(parsedResponse).build();
        }
        else {
            httpRes = Response.status(201).entity(response).build();
        }
        return httpRes;
    }
}