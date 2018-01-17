package core.persistence;

import core.user.Customer;

import java.util.*;

/**
 * The store containing all users created in the DTUPay application
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
     * @param customer The customer to be saved in a map
     */
    public void saveCustomer(Customer customer){
        customers.put(customer.getUserID(), customer);
    }


    /**
     * @param uuid The UUID of the customer that needs to be retrieved
     * @return The Customer object with the given UUID
     */
    public Customer getCustomer(UUID uuid){
        return customers.get(uuid);
    }


    /**
     * @return The map containing all the created customers in the system
     */
    public Map<UUID, Customer> getCustomers(){
        return customers;
    }

    /**
     * Deletes all customers from the store
     */
    public void clearStore(){
        customers.clear();
    }


    /**
     * Since no two users with the same cpr should be created, this method looks through the store to check if any
     * customer exists with a cpr matching that of the supplied Customer object
     * @param customer The customer object from which we will check if it's cpr already exist
     * @return Whether a user with a cpr number already exists in the CustomerStore
     */
    public boolean cprExists(Customer customer){
        HashSet<String> cprNumbers = new HashSet<>();

        for (UUID key: customers.keySet()) {
            cprNumbers.add(customers.get(key).getCpr());
        }

        return cprNumbers.contains(customer.getCpr());
    }
}
