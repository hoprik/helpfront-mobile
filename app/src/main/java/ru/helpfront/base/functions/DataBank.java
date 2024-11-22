package ru.helpfront.base.functions;

import android.util.Log;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DataBank {
    private static final Map<String, Object> map = new HashMap<>();

    public static void add(String key, Object value, Boolean isStatic){
        Log.d("dataBank", "register");
        if (isStatic && map.containsKey(key)){
            return;
        }
        map.put(key, value);
    }

    public static String getString(String key){
        Object value = map.get(key);
        if(!(value instanceof String)){
            return "";
        }
        return (String) value;
    }
    public static int getInt(String key){
        Object value = map.get(key);
        if(!(value instanceof Integer)){
            return 0;
        }
        return (int) value;
    }
    public static JSONObject getJsonObject(String key){
        Object value = map.get(key);
        if(!(value instanceof JSONObject)){
            return null;
        }
        Log.d("dataBank", String.valueOf(value));
        return (JSONObject) value;
    }
    public static Boolean getBool(String key){
        Object value = map.get(key);
        if(!(value instanceof Boolean)){
            return false;
        }
        return (Boolean) value;
    }

    public static Object get(String key){
        return map.get(key);
    }
}
