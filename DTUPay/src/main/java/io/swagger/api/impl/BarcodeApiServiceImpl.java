package io.swagger.api.impl;

import io.swagger.api.*;


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

/**
 * @author Mikkel Brusen
 * A class used to forward requests to a bean and handle its responses by generating response codes for the API
 */
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

    /**
     * @param uuid The string representation of the UUID for the customer who request a barcode
     * @param securityContext 
     * @return a HTTP response code with the status of the issued operation
     * @throws NotFoundException
     */
    @Override
    public Response getCustomerBarcode(String uuid, SecurityContext securityContext) throws NotFoundException {
        JmsProvider jmsProvider = new JmsProvider();

        String response = "";
        try {
            response = jmsProvider.sendMessage(REQUEST_BARCODE_QUEUE, uuid);
        } catch (TimeoutException e) {
            return Response.serverError().entity(JmsProvider.TIMEOUT_ERROR).build();
        } catch (JMSException e) {
            return Response.serverError().entity(JmsProvider.JMS_ERROR).build();
        }

        String parsedResponse = (String) GsonWrapper.fromJson(response, String.class);

        Response httpRes;

        if (parsedResponse.equals(BarcodeResponse.NO_USER.getValue())) {
            httpRes = Response.status(403).entity(parsedResponse).build();
        } else if (parsedResponse.equals(BarcodeResponse.INVALID_INPUT.getValue())) {
            httpRes = Response.status(405).entity(parsedResponse).build();
        } else {
            httpRes = Response.status(200).entity(response).build();
        }
        return httpRes;
    }
}