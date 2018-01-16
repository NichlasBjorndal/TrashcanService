package core.persistence;

import core.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Singleton class to store users in memory
 */
public class UserStore {

    private Map<UUID, User> users;

    private static final UserStore instance = new UserStore();

    private UserStore(){
        users = new HashMap<>();
    }

    /**
     * @return A singleton instace of the object
     */
    public static UserStore getInstance(){
        return instance;
    }

    /**
     * Stores the user in a hashmap
     * @param user The user to be saved in a map
     */
    public void saveUser(User user){
        users.put(user.getUserID(), user);
    }

    /**
     * @param id UUID which can uniquely identify a user
     * @return The user with the corresponding ID
     */
    public User getUser(UUID id){
        return users.get(id);
    }

    public void clearStore() {
        users.clear();
    }
}