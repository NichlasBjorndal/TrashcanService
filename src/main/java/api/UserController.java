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
                        name = "java:/queue/UserQueueMDB",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "CreateUserQueue"
                ),
        }
)

@Path("/users")
public class UserController {
    private static final String CREATE_QUEUE = "CreateUserQueue";

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll() {

        JmsProvider jmsProvider = new JmsProvider();
        String response = null;
        try {
            response = jmsProvider.sendMessage(CREATE_QUEUE, "Hej far");
        } catch (Exception e) {
            e.printStackTrace();
            response = "error";
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        return arrayBuilder.add(response).build();
    }
}