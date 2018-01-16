package mdb;

import core.user.Customer;
import core.utils.BankServerUtil;
import core.utils.GsonWrapper;
import io.swagger.api.impl.CustomerResponse;
import mdb.utils.ProcessMessageUtil;
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
        String response = ProcessMessageUtil.createCustomer(customer);

        return GsonWrapper.toJson(response);
    }
}