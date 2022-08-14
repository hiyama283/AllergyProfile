package com.github.distriful5061.AllergyProfile.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils implements BaseUtils {
    public static String toJson(Object object, Gson gson) {
        return gson.toJson(object);
    }

    public static String toJson(Object object) {
        return toJson(object, new GsonBuilder().setPrettyPrinting().create());
    }

    public static <T> T fromJson(String object, Class<T> classOfT) {
        return new Gson().fromJson(object, classOfT);
    }
}
