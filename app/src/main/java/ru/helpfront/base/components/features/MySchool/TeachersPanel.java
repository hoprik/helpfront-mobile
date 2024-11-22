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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class TeachersPanel extends LinearLayout{
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
        getTeachers().thenAccept(jsonObjects -> {
            jsonObjects.forEach(jsonObject -> {
                try {
                    String url = "";
                    JSONObject info = jsonObject.getJSONObject("info");
                    if (jsonObject.has("personalization")){
                        JSONObject personalization = jsonObject.getJSONObject("personalization");
                        url = "https://www.helpfront.ru"+personalization.getString("avatar").split("www")[1];
                    }
                    this.addView(new UserCart(context, String.format("%s %s", info.getString("name"), info.getString("surname")), "Я тут учитель", url));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
        });

    }

    public CompletableFuture<List<JSONObject>> getTeachers() {
        CompletableFuture<List<JSONObject>> future = new CompletableFuture<>();

        Network.sendPOST("api/user/getList", "{}", Functions.getCookie(), new Network.Callback() {
            @Override
            public void onFailure(IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException, JSONException {
                List<JSONObject> teachers = new ArrayList<>();
                JSONObject object = new JSONObject(response.body().string());
                if (!object.has("data")) {
                    return;
                }
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject user = array.getJSONObject(i);
                    String role = user.getString("role");
                    if (role.equals("teacher")) {
                        Log.d("users", user.toString());
                        teachers.add(user);
                    }
                }
                future.complete(teachers);
            }
        });

        return future;
    }
}
