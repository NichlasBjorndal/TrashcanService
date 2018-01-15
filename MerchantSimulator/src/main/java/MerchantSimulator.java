import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.model.Transaction;
import mdb.utils.GsonWrapper;

public class MerchantSimulator {
    //public static String endpoint = "http://159.89.18.108:8080/DTUPay-0.5/api";
    public static String endpoint = "http://localhost:8080/DTUPay_war/api";

    public MerchantSimulator(){}

    public ResponseModel payMerchant(Transaction transaction) throws UnirestException {
        //make json
        HttpResponse<String> r = Unirest.post(endpoint + "/pay").header("Content-Type", "application/json").body(GsonWrapper.toJson(transaction)).asString();

        return new ResponseModel(r.getStatus(), r.getBody());

    }
}
