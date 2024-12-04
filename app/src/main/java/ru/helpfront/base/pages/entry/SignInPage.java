package ru.helpfront.base.pages.entry;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.Network;
import ru.helpfront.base.pages.Page;
import ru.helpfront.base.types.Const;

import java.io.IOException;

public class SignInPage extends Page {
    public SignInPage(ComponentActivity activity, int renderId) {
        super(activity, renderId);
    }

    @Override
    public void render() {
        Functions.changeLayout(this.activity, R.layout.register, renderId);
        this.activity.findViewById(R.id.registerButton).setOnClickListener(click());
    }

    private String convertName(String name){
        String cleanedName = name.trim();
        if (!cleanedName.isEmpty()) {
            cleanedName = cleanedName.substring(0, 1).toUpperCase() + cleanedName.substring(1).toLowerCase();
        }
        return cleanedName;
    }

    @SuppressLint("StringFormatMatches")
    private boolean validateUser(String name, String surname, String email, String login, String password, String retryPassword){
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty() || retryPassword.isEmpty()){
            Toast.makeText(activity, activity.getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(retryPassword)){
            Toast.makeText(activity, activity.getString(R.string.error_password_mismatch), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < Const.MIN_LENGTH_PASSWORD){
            Toast.makeText(activity, String.format(activity.getString(R.string.error_password_length), Const.MIN_LENGTH_PASSWORD), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Const.RUS_WORD_PATTERN.matcher(name).matches()){
            Toast.makeText(activity, activity.getString(R.string.error_name_format), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Const.RUS_WORD_PATTERN.matcher(surname).matches()) {
            Toast.makeText(activity, activity.getString(R.string.error_surname_format), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Const.EMAIL_PATTERN.matcher(email).matches()) {
            Toast.makeText(activity, activity.getString(R.string.error_email_format), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Const.LOGIN_PATTERN.matcher(login).matches()) {
            Toast.makeText(activity, String.format(activity.getString(R.string.error_login_format), Const.MIN_LENGTH_LOGIN, Const.MAX_LENGTH_LOGIN), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private View.OnClickListener click(){
        return v -> {
            String regFName = ((EditText)activity.findViewById(R.id.regFName)).getText().toString();
            String regLName = ((EditText)activity.findViewById(R.id.regLName)).getText().toString();
            String regEmail = ((EditText)activity.findViewById(R.id.regEmail)).getText().toString();
            String regLogin = ((EditText)activity.findViewById(R.id.regLogin)).getText().toString();
            String regPasswordF = ((EditText)activity.findViewById(R.id.regPasswordF)).getText().toString();
            String regPasswordL = ((EditText)activity.findViewById(R.id.regPasswordL)).getText().toString();

            if (!validateUser(convertName(regFName), convertName(regLName), regEmail, regLogin, regPasswordF, regPasswordL)){
                return;
            }

            Network.sendPOST("api/user/create", String.format("{\"name\":\"%s\",\"surname\":\"%s\",\"email\":\"%s\",\"login\":\"%s\",\"password\":\"%s\",\"retryPassword\":\"%s\"}", convertName(regFName), convertName(regLName), regEmail, regLogin, regPasswordF, regPasswordL), "", response -> {
                JSONObject data = new JSONObject(response.body().string());
                String tempId = data.optString("data", "");
                if (tempId.isEmpty()){
                    Toast.makeText(activity, activity.getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                    return;
                }
                new ConfirmPage(activity, renderId, tempId);
            });
        };
    }
}
