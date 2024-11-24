package ru.helpfront.base.components.features.MySchool;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.functions.DataBank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        getTeachers().forEach(jsonObject -> this.addView(new UserCard(context, jsonObject, true)));
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
                    Log.d("debugTeacher", jsonObject.toString());
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        return teachers;
    }
}
