package core.user;

import core.barcode.Model.Barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to model a customer user
 */
public class Customer extends User {
    private String cpr;
    private List<Barcode> barcodes;


    /**
     * @param firstname
     * @param lastname
     * @param cpr
     */
    public Customer(String firstname, String lastname, String cpr) {
        super();
        this.setFirstName(firstname);
        this.setLastName(lastname);
        this.setCpr(cpr);
        barcodes = new ArrayList<>();
    }

    public Customer() {
        super();
        barcodes = new ArrayList<>();
    }

    /**
     * @return cpr number of customer
     */
    public String getCpr() {
        return cpr;
    }

    /**
     * @param cpr new cpr number of customer
     */
    public void setCpr(String cpr) {
        this.cpr = cpr;
    }


    public List<Barcode> getBarcodes() {
        return barcodes;
    }
}
