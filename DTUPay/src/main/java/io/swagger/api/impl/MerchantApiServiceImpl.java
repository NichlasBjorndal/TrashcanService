package io.swagger.api.impl;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.swagger.api.*;


import io.swagger.model.Merchant;

import io.swagger.api.NotFoundException;
import mdb.utils.BankserverUtil;
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
    core.user.Merchant lastMerchant;

    @Override
    public Response createMerchant(Merchant body, SecurityContext securityContext) throws NotFoundException {
        core.user.Merchant merchant = new core.user.Merchant(body.getCvr(), body.getFirstName(), body.getLastName());

        if (!body.getCvr().matches("^(?!\\s*$)[0-9\\s]{8}$"))
            return Response.status(405).entity("Invalid input").build();

        if ((body.getFirstName().equals(" ") || body.getLastName().equals(" ")) || (!body.getLastName().matches("^[a-zA-Z0-9 ]*$") || !body.getFirstName().matches("^[a-zA-Z0-9 ]*$")))
            return Response.status(405).entity("Invalid input").build();

        if (!merchant.equals(lastMerchant))
            lastMerchant = merchant;
        else
            return Response.status(400).entity("An error occurred with the account").build();


        try {
            BankserverUtil.GetServer().getAccountByCprNumber(merchant.getCvr());
        } catch (BankServiceException_Exception e) {
            return Response.status(403).entity("Merchant doesn't have bank account").build();
        }


        return Response.status(201).entity("65980983").build();
    }
}
