package ru.helpfront.base.pages.profile;

import androidx.activity.ComponentActivity;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.pages.Page;

public class School extends Page {
    public School(ComponentActivity activity, int renderId) {
        super(activity, renderId);
    }

    @Override
    public void render() {
        Functions.changeLayout(this.activity, R.layout.school, renderId);
    }
}
