package core.user;


import java.util.UUID;

/**
 *  An object defining the user
 */
public class User {

    private String firstName, lastName;
    private UUID userID;
    private Double accountBalance;


    //Used for GSON
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
}

