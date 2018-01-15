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
     * @param name
     * @param cpr
     */
    public Customer(String name, String cpr) {
        super();
        this.setFirstName(name);
        this.setCpr(cpr);
        barcodes = new ArrayList<>();
    }

    public Customer() {
        super();
        barcodes = new ArrayList<>();
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }
}
