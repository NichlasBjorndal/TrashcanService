package core.utils;

import com.google.gson.Gson;

/**
 * A wrapper for the Gson library. This class converts java objects to json.
 */
public class GsonWrapper {
    private static Gson gson;

    static {
        gson = new Gson();
    }

    /**
     * @param object Object to be converted to Json
     * @return Json for the input object
     */
    public static String toJson(Object object){
        return gson.toJson(object);
    }

    /**
     * @param jsonString String in Json format
     * @param objClass Class of the desired java object that the json string should be converted to.
     * @return Converted java object from the Json string
     */
    public static Object fromJson(String jsonString, Class objClass){
        return gson.fromJson(jsonString, objClass);
    }
}
