package ru.helpfront.base.pages;

import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import androidx.activity.ComponentActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import ru.helpfront.base.R;
import ru.helpfront.base.components.ui.Button;

public class EntryPage extends Page{

    public EntryPage(ComponentActivity activity) {
        super(activity);
    }

    @Override
    public void render() {
        this.activity.setContentView(R.layout.entry);

        ConstraintLayout backgroundAnimation = activity.findViewById(R.id.bgLayout);
        backgroundAnimation.setBackgroundResource(R.drawable.background_animation);
        AnimationDrawable backgroundAnimationDrawable = (AnimationDrawable) backgroundAnimation.getBackground();
        backgroundAnimationDrawable.setEnterFadeDuration(10);
        backgroundAnimationDrawable.setExitFadeDuration(5000);
        backgroundAnimationDrawable.start();


        this.activity.findViewById(R.id.loginChangeButton).setOnClickListener(change("login"));
        this.activity.findViewById(R.id.registerChangeButton).setOnClickListener(change("register"));
    }

    private View.OnClickListener change(String changeStatus) {
        return view -> {
            Button oppositeButton;

            // Определяем, какой макет и кнопку использовать
            if (changeStatus.equals("login")) {
                oppositeButton = activity.findViewById(R.id.registerChangeButton); // Используем activity для поиска
                changeLayout(R.layout.login);
            } else {
                oppositeButton = activity.findViewById(R.id.loginChangeButton); // Используем activity для поиска
                changeLayout(R.layout.register);
            }

            // Проверяем, что oppositeButton не равен null
            if (oppositeButton != null) {
                oppositeButton.setBackgroundResource(R.color.transparent);
            } else {
                Log.e("EntryPage", "Opposite button not found");
            }

            // Устанавливаем фон для текущего view
            view.setBackgroundResource(R.color.bg_black_25);
        };
    }

    private void changeLayout(int layoutId) {
        ViewGroup includeLayout = activity.findViewById(R.id.view3);
        includeLayout.setBackgroundResource(R.color.transparent);
        if (includeLayout.getChildCount() > 0) {
            includeLayout.removeAllViews();
        }

        LayoutInflater inflater = LayoutInflater.from(activity);
        View newLayout = inflater.inflate(layoutId, includeLayout, false);

        includeLayout.addView(newLayout);
    }
}
