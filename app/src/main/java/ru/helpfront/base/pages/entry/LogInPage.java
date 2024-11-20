package ru.helpfront.base.pages.entry;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.R;
import ru.helpfront.base.components.ui.Button;
import ru.helpfront.base.functions.Network;
import ru.helpfront.base.pages.Page;

import java.io.IOException;

public class LogInPage extends Page {
    private EditText logLogin;
    private EditText logPassword;

    public LogInPage(ComponentActivity activity, int renderId) {
        super(activity, renderId);
    }

    @Override
    public void render() {
        Functions.changeLayout(this.activity, R.layout.login, renderId);

        // Инициализация EditText
        logLogin = this.activity.findViewById(R.id.logLogin);
        logPassword = this.activity.findViewById(R.id.logPassword);

        Button button = this.activity.findViewById(R.id.logButton);
        button.setOnClickListener(login());
    }

    private View.OnClickListener login() {
        return view -> {
            String loginText = logLogin.getText().toString();
            String passwordText = logPassword.getText().toString();
            String jsonData = "{\"login\":\"" + loginText + "\",\"email\":\"" + loginText + "\",\"password\":\"" + passwordText + "\"}";

            Network.sendPOST("api/user/login", jsonData, "", new Network.Callback() {
                @Override
                public void onFailure(IOException e) {
                    throw new RuntimeException(e);
                }

                @Override
                public void onResponse(Response response) {
                    Log.d("network", jsonData);
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body().string());
                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (object.has("error")) {
                        try {
                            Toast.makeText(activity, object.getString("error"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (object.has("data")) {
                        try {
                            JSONObject object1 = object.getJSONObject("data");
                            Toast.makeText(activity, object1.getString("userId"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        };
    }
}
