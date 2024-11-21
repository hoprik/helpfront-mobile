package ru.helpfront.base.pages;

import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import androidx.activity.ComponentActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import ru.helpfront.base.R;
import ru.helpfront.base.components.ui.Button;
import ru.helpfront.base.pages.entry.LogInPage;
import ru.helpfront.base.pages.entry.SignInPage;
import ru.helpfront.base.pages.profile.Home;

public class EntryPage extends Page{

    public EntryPage(ComponentActivity activity) {
        super(activity);
    }

    @Override
    public void render() {
        this.activity.setContentView(R.layout.entry);

        new LogInPage(this.activity, R.id.view3);
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
                oppositeButton = activity.findViewById(R.id.registerChangeButton);
                new LogInPage(this.activity, R.id.view3);
            } else {
                oppositeButton = activity.findViewById(R.id.loginChangeButton);
                new SignInPage(this.activity, R.id.view3);
            }

            if (oppositeButton != null) {
                oppositeButton.setBackgroundResource(R.color.transparent);
            } else {
                Log.e("EntryPage", "Opposite button not found");
            }

            // Устанавливаем фон для текущего view
            view.setBackgroundResource(R.color.bg_black_25);
        };
    }
}
