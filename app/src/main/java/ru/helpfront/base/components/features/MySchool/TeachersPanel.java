package ru.helpfront.base.components.features.MySchool;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.functions.DataBank;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.functions.Network;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class TeachersPanel extends LinearLayout {
    public TeachersPanel(Context context) {
        super(context);
        init(context);
    }

    public TeachersPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TeachersPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setOrientation(VERTICAL);
        this.setPadding(16, 16, 16, 16);
        getTeachers().forEach(jsonObject -> {
            try {
                String url = "";
                JSONObject info = jsonObject.getJSONObject("info");
                if (jsonObject.has("personalization")) {
                    JSONObject personalization = jsonObject.getJSONObject("personalization");
                    url = "https://www.helpfront.ru" + personalization.getString("avatar").split("www")[1];
                }
                this.addView(new UserCart(context, String.format("%s %s", info.getString("name"), info.getString("surname")), getGroupsName(jsonObject), url));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private List<JSONObject> getTeachers() {
        Map<String, JSONObject> users = (Map<String, JSONObject>) DataBank.get("users");
        List<JSONObject> teachers = new ArrayList<>();

        users.forEach((s, jsonObject) -> {
            try {
                // Проверяем, является ли пользователь учителем
                String role = jsonObject.getString("role");
                if ("teacher".equals(role)) { // Замените "teacher" на нужное значение роли
                    teachers.add(jsonObject);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        return teachers;
    }

    private String getGroupsName(JSONObject object) throws JSONException {
        List<String> groupsName = new ArrayList<>();
        JSONArray groups = object.getJSONArray("groups");
        Map<String, JSONObject> groupsMap = (Map<String, JSONObject>) DataBank.get("groups");
        for (int i = 0; i < groups.length(); i++) {
            String groupId = groups.getString(i);
            JSONObject groupJson = groupsMap.get(groupId);
            String name = groupJson.getString("name");
            int number = groupJson.getInt("number");
            groupsName.add(String.format("%s %s", name, number));
        }
        return String.join(", ", groupsName);
    }
}
