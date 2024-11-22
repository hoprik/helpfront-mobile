package ru.helpfront.base.pages.profile;

import android.util.Log;
import androidx.activity.ComponentActivity;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.DataBank;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.functions.Network;
import ru.helpfront.base.pages.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class School extends Page {
    public School(ComponentActivity activity, int renderId) {
        super(activity, renderId);
    }

    @Override
    public void render() {
        getUsers().thenAccept(unused -> getGroups().thenAccept(unused1 -> Functions.changeLayout(this.activity, R.layout.school, renderId)));

    }

    public CompletableFuture<Void> getUsers() {
        CompletableFuture<Void> future = new CompletableFuture<>();

        Network.sendPOST("api/user/getList", "{}", Functions.getCookie(), new Network.Callback() {
            @Override
            public void onFailure(IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException, JSONException {
                JSONObject object = new JSONObject(response.body().string());
                if (!object.has("data")) {
                    return;
                }
                Map<String, JSONObject> users = new HashMap<>();
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject user = array.getJSONObject(i);
                    String publicId = user.getString("publicId");
                    users.put(publicId, user);
                }
                DataBank.add("users", users, false);
                future.complete(null);
            }
        });

        return future;
    }
    public CompletableFuture<Void> getGroups() {
        CompletableFuture<Void> future = new CompletableFuture<>();

        Network.sendPOST("api/group/getList", "{}", Functions.getCookie(), new Network.Callback() {
            @Override
            public void onFailure(IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException, JSONException {
                JSONObject object = new JSONObject(response.body().string());
                if (!object.has("data")) {
                    return;
                }
                Map<String, JSONObject> groups = new HashMap<>();
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject group = array.getJSONObject(i);
                    String id = group.getString("id");
                    groups.put(id, group);
                }
                DataBank.add("groups", groups, false);
                future.complete(null);
            }
        });

        return future;
    }


}
