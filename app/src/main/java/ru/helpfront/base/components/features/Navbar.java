package ru.helpfront.base.components.features;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.pages.EntryPage;
import ru.helpfront.base.pages.profile.Home;
import ru.helpfront.base.pages.profile.School;

import java.util.HashMap;
import java.util.Map;

import static ru.helpfront.base.MainActivity.activity;

public class Navbar extends LinearLayout {
    Map<String, ImageView> buttons = new HashMap<>();

    public Navbar(Context context) {
        super(context);
        init(context);
    }

    public Navbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Navbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Navbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setBackgroundResource(R.color.secondBg);
        this.setOrientation(HORIZONTAL);
        this.setPadding(16, 16, 16, 16);

        // Добавление элементов в LinearLayout
        this.addView(addNewButton(context, R.drawable.home, R.layout.home, "home"));
        this.addView(addNewButton(context, R.drawable.school,  R.layout.school, "school"));
        this.addView(addNewButton(context, R.drawable.exit,  R.layout.entry, "exit"));

        buttons.get("home").setColorFilter(ContextCompat.getColor(activity, R.color.green), PorterDuff.Mode.SRC_IN);
    }

    private View addNewButton(Context context, int imageId, int layoutChange, String name){
        ImageView button = new ImageView(context);
        button.setImageResource(imageId); // Используйте ваш VectorDrawable
        button.setScaleType(ImageView.ScaleType.FIT_CENTER); // Сохраняет пропорции

        // Установка параметров для ImageView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                100,
                100
        );
        params.setMargins(0,0, 16,0);
        params.gravity = Gravity.CENTER; // Центрирование изображения
        button.setLayoutParams(params);
        button.setOnClickListener(layoutChange(layoutChange));
        buttons.put(name, button);
        return button;
    }

    private View.OnClickListener layoutChange(int layoutChange){
        return v -> {
            Functions.changeLayout(activity, R.layout.loading, R.id.view3);
            buttons.forEach((s, imageView) -> {
                imageView.setColorFilter(ContextCompat.getColor(activity, R.color.white), PorterDuff.Mode.SRC_IN);
            });
            ((ImageView)v).setColorFilter(ContextCompat.getColor(activity, R.color.green), PorterDuff.Mode.SRC_IN);
            if (R.layout.home == layoutChange){
                new Home(activity, R.id.view3);
            }
            if (R.layout.school == layoutChange){
                new School(activity, R.id.view3);
            }
            if (R.layout.entry == layoutChange){
                SharedPreferences sharedPreferences = activity.getSharedPreferences("helpfrontData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", "");
                editor.apply();
                new EntryPage(activity);
            }
        };
    }
}