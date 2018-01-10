package api;

import Core.User.User;
import jsmprovider.JmsProviderUser;

import javax.jms.JMSException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/users")
public class UserController {
    private User user;


   /* @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll(){

        user = new User("Karl", "1234567891");

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        arrayBuilder.add(Json.createObjectBuilder().add("name", user.getName()));
        arrayBuilder.add(Json.createObjectBuilder().add("name", "Bobby"));
        arrayBuilder.add(Json.createObjectBuilder().add("name", "Sven"));


        user = new User("Name", "4201234");

        return arrayBuilder.build();
    }*/

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll(){

        JmsProviderUser jmsProviderUser = new JmsProviderUser();

        try {
            jmsProviderUser.SendMessage(jmsProviderUser.getCreateQueue(), "Hej far");
        } catch (JMSException e) {
            e.printStackTrace();
        }

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        return arrayBuilder.add("hej mor").build();
    }


}
