package mdb;

import core.user.Merchant;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.swagger.api.impl.MerchantApiServiceImpl;
import mdb.utils.BankServerUtil;
import mdb.utils.GsonWrapper;
import core.persistence.MerchantStore;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

/**
 * Message Driven Bean for creating merchants
 */
@MessageDriven(name = "CreateMerchantMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/CreateMerchantQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})

public class CreateMerchantMDB extends BaseMDB {
    /**
     * @param receivedText text received from CreateMerchantQueue
     * @return JSON response
     */
    @Override
    protected String processMessage(String receivedText) {
        Merchant merchant = (Merchant) GsonWrapper.fromJson(receivedText, Merchant.class);
        MerchantStore instance = MerchantStore.getInstance();
        String response;

        if (inputIsInvalid(merchant)) {
            response = MerchantApiServiceImpl.INVALID_INPUT;
        } else if (instance.getMerchant(merchant.getCvr()) == null) {
            if (BankServerUtil.checkIfBankAccountExistsById(merchant.getCvr())) {
                instance.saveMerchant(merchant);
                response = merchant.getUserID().toString();
            } else {
                response = MerchantApiServiceImpl.NO_BANK_ACCOUNT;
            }
        } else {
            response = MerchantApiServiceImpl.ALREADY_EXISTS;
        }

        return GsonWrapper.toJson(response);
    }

    private boolean inputIsInvalid(Merchant merchant) {
        return (!merchant.getCvr().matches("^(?!\\s*$)[0-9\\s]{8}$")) || ((merchant.getFirstName().equals(" ") || merchant.getLastName().equals(" ")) ||
                (!merchant.getLastName().matches("^[a-zA-Z0-9 ]*$") || !merchant.getFirstName().matches("^[a-zA-Z0-9 ]*$")));
    }
}