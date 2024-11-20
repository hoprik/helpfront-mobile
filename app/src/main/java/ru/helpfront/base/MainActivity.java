package ru.helpfront.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import ru.helpfront.base.pages.EntryPage;
import ru.helpfront.base.pages.ProfilePage;

public class MainActivity extends ComponentActivity{
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("helpfrontData", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userId", "");
        if (userID.isEmpty()){
            new EntryPage(this);
        }
        else{
            new ProfilePage(this);
        }

    }
}
