package mdb;

import core.user.Merchant;
import io.swagger.api.impl.MerchantApiServiceImpl;
import core.utils.BankServerUtil;
import core.utils.GsonWrapper;
import io.swagger.api.impl.MerchantResponse;
import persistence.MerchantStore;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

/**
 * Message Driven Bean for creating merchants. Listens to the CreateMerchantQueue.
 */
@MessageDriven(name = "CreateMerchantMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/CreateMerchantQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})

public class CreateMerchantMDB extends BaseMDB {
    /**
     * @param receivedText JSON received from CreateMerchantQueue.
     * @return new merchant id if successful, otherwise relevant error message.
     */
    @Override
    protected String processMessage(String receivedText) {
        Merchant merchant = (Merchant) GsonWrapper.fromJson(receivedText, Merchant.class);
        MerchantStore instance = MerchantStore.getInstance();
        String response;

        if (inputIsInvalid(merchant)) {
            response = MerchantResponse.INVALID_INPUT.getValue();
        } else if (instance.getMerchant(merchant.getCvr()) != null) {
            response = MerchantResponse.ALREADY_EXISTS.getValue();
        } else if (!BankServerUtil.checkIfBankAccountExistsById(merchant.getCvr())) {
            response = MerchantResponse.NO_BANK_ACCOUNT.getValue();
        } else {
            instance.saveMerchant(merchant);
            response = merchant.getUserID().toString();
        }

        return GsonWrapper.toJson(response);
    }

    private boolean inputIsInvalid(Merchant merchant) {
        return (!merchant.getCvr().matches("^(?!\\s*$)[0-9\\s]{8}$")) || ((merchant.getFirstName().equals(" ") || merchant.getLastName().equals(" ")) ||
                (!merchant.getLastName().matches("^[a-zA-Z0-9 ]*$") || !merchant.getFirstName().matches("^[a-zA-Z0-9 ]*$")));
    }
}