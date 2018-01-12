package simulator;

import Core.User.User;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import mdb.utils.GsonWrapper;

public class ClientSimulator {


    public static String endpoint = "http://virtserver.swaggerhub.com/TrashcanService/DTUPay/1.0.0";

    public ClientSimulator(){}


    public String createAccount() throws UnirestException {

        //make json
        HttpResponse<String> r = Unirest.post(endpoint + "/accounts").header("Content-Type", "application/json").body(GsonWrapper.toJson(new User("",""))).asString();

        return "123412341234123412341234123412341234";
    }

    public User getAccount(String s) {
        return new User("","");
    }
}