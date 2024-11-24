package ru.helpfront.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.functions.DataBank;
import ru.helpfront.base.functions.Network;
import ru.helpfront.base.pages.EntryPage;
import ru.helpfront.base.pages.Page;
import ru.helpfront.base.pages.ProfilePage;
import ru.helpfront.base.types.User;

import java.io.IOException;

public class MainActivity extends ComponentActivity{
    public static MainActivity activity;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        SharedPreferences sharedPreferences = getSharedPreferences("helpfrontData", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userId", "");
        if (userID.isEmpty()){
            new EntryPage(this);
        }
        else{
            init(userID);
        }
    }

    private void init(String userID){
        ComponentActivity activity = this;
        Network.sendPOST("api/user/getOne", "{}", "user_id=" + userID + ";", new Network.Callback() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException, JSONException {
                JSONObject resData = new JSONObject(response.body().string());
                JSONObject data = resData.getJSONObject("data");
                JSONObject userJson = data.getJSONObject("user");
                User user = new User();
                user.parse(userJson);
                DataBank.add("user", user, true);
                new ProfilePage(activity);
            }
        });
    }
}
