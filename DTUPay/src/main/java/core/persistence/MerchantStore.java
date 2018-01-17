package core.persistence;

import core.user.Merchant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathias Linde
 * The store containing all merchants created in the DTUPay application
 */
public class MerchantStore {
    private Map<String, Merchant> Merchants;
    private static final MerchantStore instance = new MerchantStore();

    private MerchantStore(){
        Merchants = new HashMap<>();
    }

    /**
     * @return A singleton instance of the object
     */
    public static MerchantStore getInstance(){
        return instance;
    }

    /**
     * Stores the Merchant in a hashmap
     * @param Merchant The Merchant to be saved in the store
     */
    public void saveMerchant(Merchant Merchant){
        Merchants.put(Merchant.getCvr(), Merchant);
    }

    /**
     * @param cvr The Merchant's Cvr number
     * @return The Merchant with that Cvr number
     */
    public Merchant getMerchant(String cvr){
        return Merchants.get(cvr);
    }

    /**
     * Deletes all merchants from the store
     */
    public void clearStore(){
        Merchants.clear();
    }


    /**
     * @return The map containing all created Merchants in the system
     */
    public Map<String, Merchant> getMerchants(){
        return Merchants;
    }
}