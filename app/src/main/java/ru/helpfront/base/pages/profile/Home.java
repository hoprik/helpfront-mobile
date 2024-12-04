package ru.helpfront.base.pages.profile;

import android.view.View;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.R;
import ru.helpfront.base.components.features.Sidebar.UserProfile;
import ru.helpfront.base.functions.DataBank;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.pages.Page;
import ru.helpfront.base.types.User;

public class Home extends Page {
    User user;
    public Home(ComponentActivity activity, int renderId) {
        super(activity, renderId);
        this.user = ((User)DataBank.get("user"));

    }

    @Override
    public void render() {
        Functions.changeLayout(this.activity, R.layout.home, renderId);
        this.activity.findViewById(R.id.button2).setOnClickListener(click());
        try {
            greating();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private View.OnClickListener click(){
        return v -> {
            new UserProfile(user);
        };
    }

    private void greating() throws JSONException {
        JSONObject info = DataBank.getJsonObject("info");
        ((TextView) this.activity.findViewById(R.id.greating)).setText(Functions.getText(R.string.greating)+", "+ ((User)DataBank.get("user")).getName());
    }
}
