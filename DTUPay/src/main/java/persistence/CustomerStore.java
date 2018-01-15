package persistence;

import core.user.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * Deletes all users from the store
     */
    public void clearStore(){
        customers.clear();
    }
}
