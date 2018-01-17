package mdb.utils;

import core.user.Merchant;
import core.utils.BankServerUtil;
import io.swagger.api.impl.MerchantResponse;
import core.persistence.MerchantStore;

/**
 * Contains methods for handling messages received on merchant MDBs.
 */
public class MerchantMessageHandler {
    /**
     * @param merchant merchant object to be created in the system.
     * @return UUID for the created merchant or error message if relevant.
     */
    public static String createMerchant(Merchant merchant) {
        MerchantStore instance = MerchantStore.getInstance();

        if (merchantInputIsInvalid(merchant)) {
            return MerchantResponse.INVALID_INPUT.getValue();
        } else if (instance.getMerchant(merchant.getCvr()) != null) {
            return MerchantResponse.ALREADY_EXISTS.getValue();
        } else if (!BankServerUtil.checkIfBankAccountExistsById(merchant.getCvr())) {
            return MerchantResponse.NO_BANK_ACCOUNT.getValue();
        } else {
            instance.saveMerchant(merchant);
            return merchant.getUserID().toString();
        }
    }

    /**
     * @param merchant merchant object to be checked.
     * @return whether the first name, last name and cpr of the merchant object has correct structure.
     */
    private static boolean merchantInputIsInvalid(Merchant merchant) {
        boolean invalidCvr = (!merchant.getCvr().matches("^(?!\\s*$)[0-9\\s]{8}$"));
        boolean invalidFirstName = (merchant.getFirstName() == null || merchant.getFirstName().isEmpty()) || !merchant.getFirstName().matches("^[a-zA-Z0-9 ]*$");
        boolean invalidLastName = (merchant.getLastName() == null || merchant.getLastName().isEmpty()) || (!merchant.getLastName().matches("^[a-zA-Z0-9 ]*$"));

        return invalidCvr ||invalidFirstName || invalidLastName;
    }
}