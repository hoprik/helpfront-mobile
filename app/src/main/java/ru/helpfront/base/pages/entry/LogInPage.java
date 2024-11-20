package ru.helpfront.base.pages.entry;

import androidx.activity.ComponentActivity;
import ru.helpfront.base.Functions;
import ru.helpfront.base.R;
import ru.helpfront.base.pages.Page;

public class LogInPage extends Page {
    public LogInPage(ComponentActivity activity, int renderId) {
        super(activity, renderId);
    }

    @Override
    public void render() {
        Functions.changeLayout(this.activity, R.layout.login, renderId);
    }
}
