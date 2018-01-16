package mdb;

import core.user.Customer;
import mdb.utils.GsonWrapper;
import core.persistence.UserStore;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(name = "CreateUserMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/CreateUserQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})

public class CreateUserMDB extends BaseMDB {
    private UserStore userStore = UserStore.getInstance();

    @Override
    protected String processMessage(String receivedText) {
        Customer cust = (Customer) GsonWrapper.fromJson(receivedText, Customer.class);

        userStore.saveUser(cust);
        return GsonWrapper.toJson(cust);

    }
}
