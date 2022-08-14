package gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    public static String toJson(Object object, Gson gson) {
        return gson.toJson(object);
    }

    public static String toJson(Object object) {
        return toJson(object, new GsonBuilder().setPrettyPrinting().create());
    }

    public static <T> Object fromJson(String object, Class<T> classOfT) {
        return new Gson().fromJson(object, classOfT);
    }
}
