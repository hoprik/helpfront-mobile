package ru.helpfront.base.functions;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.ComponentActivity;
import okhttp3.Response;
import ru.helpfront.base.R;
import ru.helpfront.base.components.features.MySchool.TeachersPanel;

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

    public static String getCookie(){
        return "user_id="+DataBank.getString("userID")+";";
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
