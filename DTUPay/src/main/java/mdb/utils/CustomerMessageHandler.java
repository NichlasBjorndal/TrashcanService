package mdb.utils;

import core.user.Customer;
import core.utils.BankServerUtil;
import io.swagger.api.impl.CustomerResponse;
import persistence.CustomerStore;

public class CustomerMessageHandler {
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

    private static boolean isValidCustomerInput(Customer customer) {
        boolean isValidName = !(customer.getFirstName() == null || customer.getFirstName().isEmpty() || customer.getLastName() == null || customer.getLastName().isEmpty());
        boolean isValidCpr = customer.getCpr().length() == 10 && customer.getCpr().matches("[0-9]+");
        return isValidCpr && isValidName;
    }
}