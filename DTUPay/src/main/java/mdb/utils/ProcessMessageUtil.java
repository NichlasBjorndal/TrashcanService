package mdb.utils;

import core.user.Customer;
import core.user.Merchant;
import core.utils.BankServerUtil;
import io.swagger.api.impl.CustomerResponse;
import io.swagger.api.impl.MerchantResponse;
import persistence.CustomerStore;
import persistence.MerchantStore;

public class ProcessMessageUtil {
    public static String createCustomer(Customer customer) {
        CustomerStore instance = CustomerStore.getInstance();

        if (!isValidCustomerInput(customer)) {
            return CustomerResponse.INVALID_INPUT.getValue();
        } else if (instance.cprExists(customer)) {
            return CustomerResponse.ALREADY_EXISTS.getValue();
        } else if (!BankServerUtil.checkIfBankAccountExistsById(customer.getCpr())) {
            return CustomerResponse.NO_BANK_ACCOUNT.getValue();
        } else {
            instance.saveCustomer(customer);
            return customer.getUserID().toString();
        }
    }

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

    private static boolean isValidCustomerInput(Customer customer) {
        boolean isValidName = !(customer.getFirstName() == null || customer.getFirstName().isEmpty() || customer.getLastName() == null || customer.getLastName().isEmpty());
        boolean isValidCpr = customer.getCpr().length() == 10 && customer.getCpr().matches("[0-9]+");
        return isValidCpr && isValidName;
    }

    private static boolean merchantInputIsInvalid(Merchant merchant) {
        return (!merchant.getCvr().matches("^(?!\\s*$)[0-9\\s]{8}$")) || ((merchant.getFirstName().equals(" ") || merchant.getLastName().equals(" ")) ||
                (!merchant.getLastName().matches("^[a-zA-Z0-9 ]*$") || !merchant.getFirstName().matches("^[a-zA-Z0-9 ]*$")));
    }
}
