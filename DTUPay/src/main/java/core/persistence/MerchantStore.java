package core.persistence;

import core.user.Merchant;

import java.util.HashMap;
import java.util.Map;

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
     * @param Merchant The Merchant to be saved in a map
     */
    public void saveMerchant(Merchant Merchant){
        Merchants.put(Merchant.getCvr(), Merchant);
    }

    /**
     * @param Cvr The Merchant's Cvr number
     * @return The Merchant with that Cvr number
     */
    public Merchant getMerchant(String Cvr){
        return Merchants.get(Cvr);
    }

    /**
     * Deletes all users from the store
     */
    public void clearStore(){
        Merchants.clear();
    }
}