package io.swagger.api.impl;

import io.swagger.api.*;


import io.swagger.model.Merchant;

import io.swagger.api.NotFoundException;
import jsmprovider.JmsProvider;
import core.utils.GsonWrapper;

import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.JMSException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.concurrent.TimeoutException;

@Stateless
@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/CreateMerchantMDB",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "CreateMerchantQueue"
                )}
)
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class MerchantApiServiceImpl extends MerchantApiService {
    private static final String CREATE_MERCHANT_QUEUE = "CreateMerchantQueue";

    core.user.Merchant lastMerchant;

    @Override
    public Response createMerchant(Merchant body, SecurityContext securityContext) throws NotFoundException {
        JmsProvider jmsProvider = new JmsProvider();

        String response = "";
        try {
            response = jmsProvider.sendMessage(CREATE_MERCHANT_QUEUE, body);
        } catch (TimeoutException e) {
            return Response.serverError().entity(JmsProvider.TIMEOUT_ERROR).build();
        } catch (JMSException e) {
            return Response.serverError().entity(JmsProvider.JMS_ERROR).build();
        }

        String parsedResponse = (String) GsonWrapper.fromJson(response, String.class);

        Response httpRes;
        if (parsedResponse.equals(MerchantResponse.ALREADY_EXISTS.getValue())) {
            httpRes = Response.status(400).entity(parsedResponse).build();
        } else if (parsedResponse.equals(MerchantResponse.NO_BANK_ACCOUNT.getValue())) {
            httpRes = Response.status(403).entity(parsedResponse).build();
        } else if (parsedResponse.equals(MerchantResponse.INVALID_INPUT.getValue())) {
            httpRes = Response.status(405).entity(parsedResponse).build();
        } else {
            httpRes = Response.status(201).entity(response).build();
        }
        return httpRes;
    }
}