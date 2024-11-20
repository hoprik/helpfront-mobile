package ru.helpfront.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.ComponentActivity;

public class Functions {
    private void changeLayout(ComponentActivity activity, int layoutId, int renderId) {
        ViewGroup includeLayout = activity.findViewById(renderId);
        includeLayout.setBackgroundResource(R.color.transparent);
        if (includeLayout.getChildCount() > 0) {
            includeLayout.removeAllViews();
        }

        LayoutInflater inflater = LayoutInflater.from(activity);
        View newLayout = inflater.inflate(layoutId, includeLayout, false);

        includeLayout.addView(newLayout);
    }
}
