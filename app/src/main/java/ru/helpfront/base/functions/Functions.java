package ru.helpfront.base.functions;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.ComponentActivity;
import okhttp3.Response;
import ru.helpfront.base.MainActivity;
import ru.helpfront.base.R;
import ru.helpfront.base.components.features.MySchool.TeachersPanel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class Functions {
    public static void changeLayout(ComponentActivity activity, int layoutId, int renderId) {
        ViewGroup includeLayout = activity.findViewById(renderId);
        includeLayout.setBackgroundResource(R.color.transparent);
        if (includeLayout.getChildCount() > 0) {
            includeLayout.removeAllViews();
        }

        LayoutInflater inflater = LayoutInflater.from(activity);
        View newLayout = inflater.inflate(layoutId, includeLayout, false);

        includeLayout.addView(newLayout);
    }

    public static void changeLayout(ComponentActivity activity, View layout, int renderId) {
        ViewGroup includeLayout = activity.findViewById(renderId);
        includeLayout.setBackgroundResource(R.color.transparent);
        if (includeLayout.getChildCount() > 0) {
            includeLayout.removeAllViews();
        }

        includeLayout.addView(layout);
    }

    public static String getCookie(){
        return "user_id="+DataBank.getString("userID")+";";
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        InputStream URLcontent = null;
        try {
            URLcontent = (InputStream) new URL(url).getContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Drawable image = Drawable.createFromStream(URLcontent, url);
        return image;
    }

    public static int getRandomColor(){
        Random rand = new Random();
        int n = rand.nextInt(100);
        n+=1;
        if (n < 26){
            return R.color.red;
        }
        if (n > 26 && n < 51){
            return R.color.green;
        }
        if (n > 51 && n < 76){
            return R.color.yellow;
        }
        if (n > 76 && n < 100){
            return R.color.blue;
        }
        return R.color.red;
    }

    public static String getText(int id){
        return MainActivity.activity.getResources().getString(id);
    }
}
