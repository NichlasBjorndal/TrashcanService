package core.user;

import core.barcode.Model.Barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to model a customer user account.
 * A customer account has besides the fields from a generic user account also associated a CPR-number
 * and a list of barcodes used for payment.
 */
public class Customer extends User {
    private String cpr;
    private List<Barcode> barcodes;


    /**
     * @param firstname specifies the user's first name
     * @param lastname specifies the user's last name
     * @param cpr specifies the user's (danish) CPR-number
     * An ArrayList is instantiated to hold barcodes for the user
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


    /**
     * @return a list of the barcodes generated for this account
     */
    public List<Barcode> getBarcodes() {
        return barcodes;
    }


    /**
     * @param b removes a barcode from the list of barcodes for this account
     */
    public void removeBarcode(Barcode b) {
        barcodes.remove(b);
    }
}
