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
            Network.initProfilePage(userID, this);
        }
    }


}
