package jsmprovider;

import javax.annotation.Resource;
import javax.jms.Queue;




public class JmsProviderUser extends JmsProvider {

    private final String CREATE_QUEUE = "CreateUserQueue";


    public String getCreateQueue() {
        return CREATE_QUEUE;
    }

}
