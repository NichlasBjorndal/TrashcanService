package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;


import java.util.List;

import io.swagger.api.NotFoundException;
import jsmprovider.JmsProvider;
import mdb.utils.GsonWrapper;

import java.io.InputStream;

import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Stateless
@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/RequestBarcodeMDB",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "RequestBarcodeQueue"
                )}
)

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:38:28.848Z")
public class BarcodeApiServiceImpl extends BarcodeApiService {

    private static final String REQUEST_BARCODE_QUEUE = "RequestBarcodeQueue";

    @Override
    public Response getCustomerBarcode(String uuid, SecurityContext securityContext) throws NotFoundException {
        JmsProvider jmsProvider = new JmsProvider();

        String response = "";
        try {
            response = jmsProvider.sendMessage(REQUEST_BARCODE_QUEUE, uuid);
        } catch (Exception e) {
            return Response.serverError().build();
        }

        String parsedResponse = (String) GsonWrapper.fromJson(response, String.class);

        Response httpRes;

        if (parsedResponse.equals("userDoesntExist")) {
            httpRes = Response.status(403).build();
        } else if (parsedResponse.equals("invalidInput")) {
            httpRes = Response.status(405).build();
        } else {
            httpRes = Response.status(200).entity(response).build();
        }
        return httpRes;
    }
}
