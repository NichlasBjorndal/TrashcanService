import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MerchantSimulator {
    public static String endpoint = "http://localhost:8080/DTUPay-0.5/api";

    public ResponseModel createAccount(String firstname, String lastname, String cvr) {
        //make json
        HttpResponse<String> r = null;
        try {
            r = Unirest.post(endpoint + "/merchant").header("Content-Type", "application/json").body("{\"firstName\": \""+firstname+"\",\"lastName\": \""+lastname+"\",\"cvr\": \""+cvr+"\"}").asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return new ResponseModel(r.getStatus(),r.getBody());
    }
}
