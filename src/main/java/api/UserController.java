package api;

import jsmprovider.JmsProvider;

import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.JMSException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Stateless
@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/CreateUserMDB",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "CreateUserQueue"
                ),
                @JMSDestinationDefinition(
                        name = "java:/queue/IBCMDB",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "IBCQueue"
        )
        }
)
@Path("/users")
public class UserController {
    private static final String CREATE_QUEUE = "CreateUserQueue";
    private static final String IBC_QUEUE = "IBCQueue";

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll() {

        JmsProvider jmsProvider = new JmsProvider();
        String response = null;
        try {
            String jsonmsg = "{\"name\":\"Karl\",\"cpr\":\"1234567\"}";
            response = jmsProvider.sendMessage(CREATE_QUEUE, jsonmsg);
        } catch (Exception e) {
            e.printStackTrace();
            response = "error";
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        return arrayBuilder.add(response).build();
    }

    @Path("/testIBC")
    @GET
    @Produces("application/json")
    public JsonArray getTestIBC() {

        JmsProvider jmsProvider = new JmsProvider();
        String response = null;
        try {
            String jsonmsg = "{\"name\":\"Karl\",\"cpr\":\"1234567\"}";
            response = jmsProvider.sendMessage(IBC_QUEUE, jsonmsg);
        } catch (Exception e) {
            e.printStackTrace();
            response = "error";
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        return arrayBuilder.add(response).build();
    }
}