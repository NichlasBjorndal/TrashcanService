import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClientSimulator {

    public static String endpoint = "http://159.89.18.108:8080/DTUPay-0.5/api";

    public ClientSimulator(){}

    public String createCustomer() throws UnirestException {
        //make json
        HttpResponse<String> r = Unirest.post(endpoint + "/customer").header("Content-Type", "application/json").body("{\"name\": \"mikkel\",\"cpr\": \"123\"}").asString();
        return r.getBody();
    }
}