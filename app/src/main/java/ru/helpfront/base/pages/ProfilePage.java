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

public class ProfilePage extends Page{

    public ProfilePage(ComponentActivity activity) {
        super(activity);
    }

    @Override
    public void render() {
        this.activity.setContentView(R.layout.profile);
        new Home(this.activity, R.id.view3);
    }
}
