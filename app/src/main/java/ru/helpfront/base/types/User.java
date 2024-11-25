package ru.helpfront.base.types;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.functions.DataBank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    String name = "Иван";
    String surname = "Иванович";
    String birthday = "01.01.2000";
    String avatar = "";
    String background = "";
    String publicId;
    String login;
    String email;
    int money;
    int thanks;
    String role;
    Boolean verifying;
    int giveThanks;
    String lastVisited;
    String telegram;
    JSONArray groups;
    int completedTasks;

    public void parse(JSONObject user) {
        if (user == null){
            return;
        }
        try {
            if (user.has("info")){
                name = user.getJSONObject("info").optString("name", "");
                surname = user.getJSONObject("info").optString("surname", "");
                birthday = user.getJSONObject("info").optString("birthday", "");
            }
            if (user.has("personalization")){
                avatar = user.getJSONObject("personalization").optString("avatar", "");
                background = user.getJSONObject("personalization").optString("background", "");
            }
            publicId = user.optString("publicId", "");
            login = user.optString("login", "");
            email = user.optString("email", "");
            money = user.optInt("money", 0);
            thanks = user.optInt("thanks", 0);
            role = user.optString("role", "");
            verifying = user.optBoolean("verifying", false);
            giveThanks = user.optInt("giveThanks", 0);
            lastVisited = user.optString("lastVisited", "");
            telegram = user.optString("telegram", "");
            groups = user.optJSONArray("groups");
            completedTasks = user.optInt("completedTasks", 0);
        } catch (JSONException e) {
            Log.d("error", e.toString());
        }
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAvatar() {
        if (avatar.isEmpty()) return "";
        return "https://www.helpfront.ru"+avatar.split("www")[1];
    }

    public String getBackground() {
        return background;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public int getMoney() {
        return money;
    }

    public int getThanks() {
        return thanks;
    }

    public String getRole() {
        return role;
    }

    public Boolean getVerifying() {
        return verifying;
    }

    public int getGiveThanks() {
        return giveThanks;
    }

    public String getLastVisited() {
        return lastVisited;
    }

    public String getTelegram() {
        return telegram;
    }

    public String getGroupsName(){
        List<String> groupsName = new ArrayList<>();
        Map<String, JSONObject> groupsMap = (Map<String, JSONObject>) DataBank.get("groups");
        if (groups.length() == 0){
            return "";
        }
        for (int i = 0; i < groups.length(); i++) {
            try {
                String groupId = groups.getString(i);
                JSONObject groupJson = groupsMap.get(groupId);
                String name = groupJson.getString("name");
                int number = groupJson.getInt("number");
                groupsName.add(String.format("%s %s", name, number));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return String.join(", ", groupsName);
    }

    public int getCompletedTasks() {
        return completedTasks;
    }
}
