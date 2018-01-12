package api;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Stateful
@JMSDestinationDefinitions(
    value = {
        @JMSDestinationDefinition(
            name = "java:/queue/CreateUserQueueMDB",
            interfaceName = "javax.jms.Queue",
            destinationName = "CreateUserQueueMDB"
        )
    }
)

/**
 * Created by Mikkel on 10/01/2018.
 */
@Path("/create-user")
public class CreateUser {
    @Resource(lookup = "java:/queue/CreateUserQueueMDB")
    private Queue uq;

    @Resource(lookup = "java:/ConnectionFactory")
    ConnectionFactory cf;

    InitialContext jndiContext = null;

    @Path("/new")
    @GET
    @Produces("application/json")
    public void start(){
        try {
            jndiContext = new InitialContext();
            cf = (ConnectionFactory) jndiContext.lookup("java:/ConnectionFactory");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        Session session = null;
        final Destination destination = uq;
        try {
            connection = cf.createConnection();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        } catch (JMSException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < 5; i++) {
                String text = "Create User: " + i;

                try {
                    Message message = session.createTextMessage(text);
                    session.createProducer(destination).send(message);
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }

        } finally {

        }

    }

}
