package ru.helpfront.base.pages.entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.DataBank;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.functions.Network;
import ru.helpfront.base.pages.Page;

import java.io.IOException;

public class ConfirmPage extends Page {
    private final String tempId;
    public ConfirmPage(ComponentActivity activity, int renderId, String tempId) {
        super(activity, renderId);
        this.tempId = tempId;
    }

    @Override
    public void render() {
        Functions.changeLayout(this.activity, R.layout.confirmuser, renderId);
        activity.findViewById(R.id.confirmButton).setOnClickListener(click());
    }

    private View.OnClickListener click(){
        return v -> {
            String code = ((EditText)activity.findViewById(R.id.confirmInput)).getText().toString();
            if (code.length() < 4){
                Toast.makeText(activity, "Код введен не полностью", Toast.LENGTH_SHORT).show();
                return;
            }

            Network.sendPOST("api/user/confirm", String.format("{\"id\":\"%s\",\"code\":\"%s\"}", tempId, code), "", response -> {
                JSONObject data = new JSONObject(response.body().string());
                String userID = data.optString("data", "");
                SharedPreferences sharedPreferences = activity.getSharedPreferences("helpfrontData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", userID);
                editor.apply();
                Network.initProfilePage(userID, activity);
            });


        };
    }
}
