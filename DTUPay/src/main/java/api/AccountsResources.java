package api;


import Core.User.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("accounts")
public class AccountsResources {

    @POST
    @Consumes({"application/json"})
    public Response createAccount(User user){
        return Response.status(400).build();
    }
}
