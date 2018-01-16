package mdb;

import core.user.Merchant;
import io.swagger.api.impl.MerchantApiServiceImpl;
import core.utils.BankServerUtil;
import core.utils.GsonWrapper;
import io.swagger.api.impl.MerchantResponse;
import mdb.utils.ProcessMessageUtil;
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
        String response = ProcessMessageUtil.createMerchant(merchant);

        return GsonWrapper.toJson(response);
    }
}