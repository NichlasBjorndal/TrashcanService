package mdb;

import core.user.User;
import mdb.utils.GsonWrapper;
import persistence.UserStore;

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
        User user = (User) GsonWrapper.fromJson(receivedText, User.class);

        userStore.saveUser(user);
        return GsonWrapper.toJson(user);

    }
}
