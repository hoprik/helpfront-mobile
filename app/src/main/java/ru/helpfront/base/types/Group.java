package ru.helpfront.base.types;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.functions.DataBank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Group {
    JSONObject leader;
    JSONObject headman;
    JSONArray array;
    public void parse(JSONObject groups) {
        leader = groups.optJSONObject("leader");
        headman = groups.optJSONObject("headman");
        array = groups.optJSONArray("users");
    }

    public JSONObject getLeader() {
        Map<String, JSONObject> map = (Map<String, JSONObject>) DataBank.get("users");
        return getUser(leader, map);
    }

    public JSONObject getHeadman() {
        Map<String, JSONObject> map = (Map<String, JSONObject>) DataBank.get("users");
        return getUser(headman, map);
    }

    public List<JSONObject> getUsers() {
        Map<String, JSONObject> map = (Map<String, JSONObject>) DataBank.get("users");
        List<JSONObject> users = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            users.add(getUser(array.optJSONObject(i), map));
        }
        return users;
    }

    private JSONObject getUser(JSONObject user, Map<String, JSONObject> map ) {
        if (user == null) return null;
        String userId = user.optString("publicId", "");
        if (userId.isEmpty()) return null;
        return map.get(userId);
    }
}
