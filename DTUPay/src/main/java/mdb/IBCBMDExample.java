package mdb;

import Core.User.User;
import jsmprovider.JmsProvider;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(name = "IBCMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/IBCQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})

public class IBCBMDExample extends BaseMDB {

    @Override
    protected String processMessage(String receivedText) {
        String response = null;
        User user = new User("bob","2982389-1234");

        JmsProvider provider = new JmsProvider();
        try {
            response = provider.sendMessage("CreateUserQueue",user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}