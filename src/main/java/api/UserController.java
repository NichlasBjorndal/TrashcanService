package api;

import jsmprovider.JmsProvider;
import javax.jms.JMSException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/users")
public class UserController {
    private static final String CREATE_QUEUE = "CreateUserQueue";

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll() {

        JmsProvider jmsProvider = new JmsProvider();

        try {
            jmsProvider.SendMessage(CREATE_QUEUE, "Hej far");
        } catch (JMSException e) {
            e.printStackTrace();
        }

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        return arrayBuilder.add("hej mor").build();
    }
}