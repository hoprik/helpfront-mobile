package ru.helpfront.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import ru.helpfront.base.functions.Network;
import ru.helpfront.base.pages.EntryPage;
import ru.helpfront.base.components.features.LoadingScreen;

public class MainActivity extends ComponentActivity{
    public static MainActivity activity;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(new LoadingScreen(this));
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
