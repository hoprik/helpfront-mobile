package ru.helpfront.base.pages.profile;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.R;
import ru.helpfront.base.components.features.sidebar.Sidebar;
import ru.helpfront.base.components.features.sidebar.UserProfile;
import ru.helpfront.base.components.ui.Button;
import ru.helpfront.base.functions.DataBank;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.pages.Page;

public class Home extends Page {
    public Home(ComponentActivity activity, int renderId) {
        super(activity, renderId);
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
            new UserProfile(this.activity, "Валера морозов (hoprik)", "Не указана", "https://www.helpfront.ru/user-1728643046594-b3e8f034-4821-423e-a535-a1c93243482b/avatar-1729081792150.png", "Ученик", "10.10.2008 (Весы)", "0", "2", "200", "0");
        };
    }

    private void greating() throws JSONException {
        JSONObject info = DataBank.getJsonObject("info");
        ((TextView) this.activity.findViewById(R.id.greating)).setText("Привет, "+ info.getString("name"));
    }
}
