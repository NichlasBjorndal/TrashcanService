package persistence;

import core.user.Merchant;

import java.util.HashMap;
import java.util.Map;

public class MerchantStore {
    private Map<String, Merchant> merchants;

    private static final MerchantStore instance = new MerchantStore();

    private MerchantStore(){
        merchants = new HashMap<String, Merchant>();
    }

    /**
     * @return A singleton instance of the object
     */
    public static MerchantStore getInstance(){
        return instance;
    }

    /**
     * Stores the merchant in a hashmap
     * @param merchant The merchant to be saved in a map
     */
    public void saveMerchant(Merchant merchant){ merchants.put(merchant.getCvr(), merchant);
    }


    /**
     * @param cvr The merchant's cpr number
     * @return The merchant with that cpr number
     */
    public Merchant getMerchant(String cvr){
        return merchants.get(cvr);
    }
}

