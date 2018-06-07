package com.pihrit.bakingapp;

import com.google.gson.Gson;

/***
 * Looked example of the util provided by the project reviewer.
 *
 @see: <a href="https://github.com/ashwanikumar04/retroclient/blob/master/android/retro-client/src/main/java/in/ashwanik/retroclient/utils/Json.java"></a>
 */
public class SerializationUtil {
    private SerializationUtil() {
        // Hiding public constructor
    }

    public static <T> String serialize(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T deSerialize(String jsonString, Class<T> classT) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, classT);
    }
}
