package ru.helpfront.base;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends ComponentActivity{
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

        // Запуск фоновой анимации
        ConstraintLayout backgroundAnimation = findViewById(R.id.bgLayout);
        backgroundAnimation.setBackgroundResource(R.drawable.background_animation);
        AnimationDrawable backgroundAnimationDrawable = (AnimationDrawable) backgroundAnimation.getBackground();
        backgroundAnimationDrawable.setEnterFadeDuration(10);
        backgroundAnimationDrawable.setExitFadeDuration(5000);
        backgroundAnimationDrawable.start();

//        webView = findViewById(R.id.hfView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("https://www.helpfront.ru/");

    }
}
