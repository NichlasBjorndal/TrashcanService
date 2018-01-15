package io.swagger.api.impl;

import io.swagger.api.*;


import io.swagger.model.Merchant;

import io.swagger.api.NotFoundException;
import jsmprovider.JmsProvider;
import mdb.utils.GsonWrapper;

import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

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
    public static final String ALREADY_EXISTS = "Merchant already exist in DTUPay";
    public static final String NO_BANK_ACCOUNT = "Merchant doesn't have bank account";
    public static final String INVALID_INPUT = "Invalid input";
    private static final String CREATE_MERCHANT_QUEUE = "CreateMerchantQueue";

    core.user.Merchant lastMerchant;

    @Override
    public Response createMerchant(Merchant body, SecurityContext securityContext) throws NotFoundException {
        JmsProvider jmsProvider = new JmsProvider();

        String response = "";
        try {
            response = jmsProvider.sendMessage(CREATE_MERCHANT_QUEUE, body);
        } catch (Exception e) {
            return Response.serverError().build();
        }

        String parsedResponse = (String) GsonWrapper.fromJson(response, String.class);

        Response httpRes;
        if (parsedResponse.equals(ALREADY_EXISTS)) {
            httpRes = Response.status(400).entity(ALREADY_EXISTS).build();
        }
        else if (parsedResponse.equals(NO_BANK_ACCOUNT)) {
            httpRes = Response.status(403).entity(NO_BANK_ACCOUNT).build();
        }
        else if (parsedResponse.equals(INVALID_INPUT)) {
            httpRes = Response.status(405).entity(INVALID_INPUT).build();
        }
        else {
            httpRes = Response.status(201).entity(response).build();
        }
        return httpRes;
    }
}
