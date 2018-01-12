package api;


import Core.User.User;
import dtu.ws.fastmoney.BankServiceException;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.rmi.RemoteException;

@Path("accounts")
public class AccountsResources {

    @POST
    @Consumes({"application/json"})
    public Response createAccount(User user)throws RemoteException, JMSException, NamingException {


        return Response.noContent().build();

    }


}
