package mdb;

import core.user.Customer;
import core.utils.BankServerUtil;
import core.utils.GsonWrapper;
import io.swagger.api.impl.CustomerResponse;
import persistence.CustomerStore;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

/**
 * Message Driven Bean for creating new customers. Listens to the CreateCustomerQueue.
 */
@MessageDriven(name = "CreateCustomerMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/CreateCustomerQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class CreateCustomerMDB extends BaseMDB {
    /**
     * @param receivedText JSON received from CreateCustomerQueue.
     * @return new customer id if successful, otherwise relevant error message.
     */
    @Override
    protected String processMessage(String receivedText) {
        Customer customer = (Customer) GsonWrapper.fromJson(receivedText, Customer.class);

        CustomerStore instance = CustomerStore.getInstance();

        String response;

        if (!isValidInput(customer)) {
            response = CustomerResponse.INVALID_INPUT.getValue();
        } else if (instance.cprExists(customer)) {
            response = CustomerResponse.ALREADY_EXISTS.getValue();
        } else if (!BankServerUtil.checkIfBankAccountExistsById(customer.getCpr())) {
            response = CustomerResponse.NO_BANK_ACCOUNT.getValue();
        } else {
            instance.saveCustomer(customer);
            response = customer.getUserID().toString();
        }
        return GsonWrapper.toJson(response);
    }

    private boolean isValidInput(Customer customer) {
        boolean isValidName = !(customer.getFirstName() == null || customer.getFirstName().isEmpty() || customer.getLastName() == null || customer.getLastName().isEmpty());
        boolean isValidCpr = customer.getCpr().length() == 10 && customer.getCpr().matches("[0-9]+");
        return isValidCpr && isValidName;
    }
}
