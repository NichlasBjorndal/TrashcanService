package core.user;


import java.util.UUID;

/**
 *  Class to model a generic user account
 *  A generic user account has associated a first name, last name, UUID and a balance
 */
public class User {

    private String firstName, lastName;
    private UUID userID;
    private Double accountBalance;

    /**
     * Constructor for instantiating a User. A UUID is generated and associated the local field.
     */
    public User(){
        this.userID = UUID.randomUUID();
    }

    /**
     * @return the user's firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the user's lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the user's unique ID
     */
    public UUID getUserID() {
        return userID;
    }

    /**
     * @param firstName specifies the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName specifies the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the current balance at the user's account
     */
    public Double getAccountBalance() {
        return accountBalance;
    }


    /**
     * @param accountBalance specifies the initial balance at the user's account
     */
    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
}

