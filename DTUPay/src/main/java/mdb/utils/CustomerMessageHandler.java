package mdb.utils;

import core.user.Customer;
import core.utils.BankServerUtil;
import io.swagger.api.impl.CustomerResponse;
import core.persistence.CustomerStore;

/**
 * Contains methods for handling messages received on customer MDBs.
 */
public class CustomerMessageHandler {
    /**
     * @param customer customer object to be created in the system.
     * @return UUID for the created customer or error message if relevant.
     */
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

    /**
     * @param customer customer object to be checked.
     * @return whether the first name, last name and cpr of the customer object has correct structure.
     */
    private static boolean isValidCustomerInput(Customer customer) {
        boolean isValidName = !(customer.getFirstName() == null || customer.getFirstName().isEmpty() || customer.getLastName() == null || customer.getLastName().isEmpty());
        boolean isValidCpr = customer.getCpr().length() == 10 && customer.getCpr().matches("[0-9]+");
        return isValidCpr && isValidName;
    }
}