package core.user;


/**
 * Class to model a merchant user
 */
public class Merchant extends User {
    private String cvr;

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
}
