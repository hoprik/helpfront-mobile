package ru.helpfront.base.pages.profile;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.R;
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
        try {
            greating();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void greating() throws JSONException {
        JSONObject info = DataBank.getJsonObject("info");
        ((TextView) this.activity.findViewById(R.id.greating)).setText("Привет, "+ info.getString("name"));
    }
}
