import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.model.Customer;
import mdb.utils.DynamicEndpointHelper;
import mdb.utils.GsonWrapper;

public class ClientSimulator {

    public static String currentEndpoint = "";

    public ClientSimulator() {
        currentEndpoint = DynamicEndpointHelper.getCurrentEndpoint();
    }

    public ResponseModel createCustomer(Customer customer) throws UnirestException {
        //make json
        HttpResponse<String> r = Unirest.post(currentEndpoint + "/customer").header("Content-Type", "application/json").body(GsonWrapper.toJson(customer)).asString();

        return new ResponseModel(r.getStatus(), r.getBody());
    }

    public ResponseModel requestBarcode(String useruuid) throws UnirestException {
        HttpResponse<String> r = Unirest.get(currentEndpoint + "/barcode/" + useruuid).asString();

        return new ResponseModel(r.getStatus(), r.getBody());
    }

    public ResponseModel clearDataStores() throws UnirestException {
        //make json
        HttpResponse<String> r = Unirest.post(currentEndpoint + "/util/flush").asString();

        return new ResponseModel(r.getStatus(), r.getBody());
    }

}