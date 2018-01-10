package jsmprovider;

import javax.annotation.Resource;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Queue;

@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/CreateUserQueue",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "CreateUserQueue"
                )
        }
)

public class JmsProviderUser extends JmsProvider {

    @Resource(lookup = "java:/queue/CreateUserQueue")
    private Queue createQueue;

    public Queue getCreateQueue() {
        return createQueue;
    }

}
