package core.user;


import java.util.UUID;

/**
 *  An object defining the user
 */
public class User {

    private String name;
    private UUID userID;
    private Double accountBalance;


    //Used for GSON
    public User(){
        this.userID = UUID.randomUUID();
    }

    /**
     * @return the user's full name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the user's unique ID
     */
    public UUID getUserID() {
        return userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
}

