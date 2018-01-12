package simulator;

import Core.User.User;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientSimulator {


    public static String endpoint = "http://virtserver.swaggerhub.com/TrashcanService/DTUPay/1.0.0";

    public ClientSimulator(){}


    public String createAccount() throws UnirestException {
        Unirest.post(endpoint + "/accounts").header("Content-Type", "application/json").body(new User("","")).asString();

        return "123412341234123412341234123412341234";
    }

    public User getAccount(String s) {
        return new User("","");
    }
}
