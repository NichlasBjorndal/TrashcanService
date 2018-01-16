package core.persistence;

import core.user.Customer;

import java.util.*;

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
     * @param uuid
     * @return
     */
    public Customer getCustomer(UUID uuid){
        return customers.get(uuid);
    }


    /**
     * @return
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
     * @param customer
     * @return Whether a user with a cpr number already exists in the userstore
     */
    public boolean cprExists(Customer customer){
        HashSet<String> cprNumbers = new HashSet<>();

        for (UUID key: customers.keySet()) {
            cprNumbers.add(customers.get(key).getCpr());
        }

        return cprNumbers.contains(customer.getCpr());
    }
}
