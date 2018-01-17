package core.persistence;

import core.user.Customer;

import java.util.*;

/**
 * Class to model a "store" of all the customers in the DTU-Pay-system
 * All Customer accounts are put in a HashMap uniquely identified by their UUID
 */
public class CustomerStore {
    private Map<UUID, Customer> customers;

    private static final CustomerStore instance = new CustomerStore();

    private CustomerStore(){
        customers = new HashMap<>();
    }

    /**
     * @return A singleton instance of the object
     */
    public static CustomerStore getInstance(){
        return instance;
    }

    /**
     * Stores the customer in a hashmap
     * @param customer The customer account to be saved in a map
     */
    public void saveCustomer(Customer customer){
        customers.put(customer.getUserID(), customer);
    }


    /**
     * @param uuid gets a specific customer from his UUID
     * @return
     */
    public Customer getCustomer(UUID uuid){
        return customers.get(uuid);
    }


    /**
     * @return the Map of all customers in the system
     */
    public Map<UUID, Customer> getCustomers(){
        return customers;
    }

    /**
     * Deletes all users from the store
     */
    public void clearStore(){
        customers.clear();
    }


    /**
     * @param customer The customer account to be investigated
     * @return Whether a customer with a cpr number already exists in the CustomerStore
     */
    public boolean cprExists(Customer customer){
        HashSet<String> cprNumbers = new HashSet<>();

        // All CPR-numbers in the CustomerStore are gathered
        for (UUID key: customers.keySet()) {
            cprNumbers.add(customers.get(key).getCpr());
        }

        return cprNumbers.contains(customer.getCpr());
    }
}
