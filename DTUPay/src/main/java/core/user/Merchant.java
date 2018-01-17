package core.user;


/**
 * @author Mathias Linde
 * Class to model a merchant user
 */
public class Merchant extends User {
    private String cvr;

    public Merchant(String cvr, String firstName, String lastName) {
        super();
        setCvr(cvr);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public Merchant() {
        super();
    }

    /**
     * @return CVR number of merchant.
     */
    public String getCvr() {
        return cvr;
    }

    /**
     * @param merchant Merchant to compare with.
     * @return Comparison result.
     */
    @Override
    public boolean equals(Object merchant){
        Merchant m = (Merchant) merchant;
        return merchant != null && (m.getCvr().equals(this.cvr) && m.getFirstName().equals(this.getFirstName()) && m.getLastName().equals(this.getLastName()));
    }

    public void setCvr(String cvr) {
        this.cvr = cvr;
    }
}
