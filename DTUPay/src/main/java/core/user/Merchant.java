package core.user;


/**
 * Class to model a merchant user
 */
public class Merchant extends User {
    private String cvr;

    public Merchant() {
        super();
    }

    /**
     * @param cvr
     * @param name
     */
    public Merchant(String cvr, String name){
        super();
        this.cvr = cvr;
        this.setName(name);
    }

    public String getCvr() {
        return cvr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr;
    }
}
