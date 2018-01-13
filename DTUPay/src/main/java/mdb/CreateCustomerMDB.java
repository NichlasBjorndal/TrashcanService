package mdb;

import core.user.Customer;
import mdb.utils.GsonWrapper;
import persistence.CustomerStore;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(name = "CreateCustomerMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/CreateCustomerQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class CreateCustomerMDB extends BaseMDB {
    @Override
    protected String processMessage(String receivedText) {

        Customer cust = (Customer) GsonWrapper.fromJson(receivedText, Customer.class);

        CustomerStore instance = CustomerStore.getInstance();
        instance.saveCustomer(cust);

        return GsonWrapper.toJson(cust.getCpr());
    }
}
