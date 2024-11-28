package ru.helpfront.base.functions;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.MainActivity;
import ru.helpfront.base.pages.ProfilePage;
import ru.helpfront.base.types.User;

import java.io.IOException;

public class Network {
    public static void sendPOST(String url, String data, String cookie, Callback callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient.Builder().build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url("https://www.helpfront.ru/" + url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(()->{
                    Toast.makeText(MainActivity.activity, "Нет подключения к сети", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response){
                handler.post(() -> {
                    try {
                        callback.onResponse(response);
                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    public static void initProfilePage(String userID, ComponentActivity activity){
        Network.sendPOST("api/user/getOne", "{}", "user_id=" + userID + ";", response -> {
            JSONObject resData = new JSONObject(response.body().string());
            JSONObject data = resData.getJSONObject("data");
            JSONObject userJson = data.getJSONObject("user");
            User user = new User();
            user.parse(userJson);
            DataBank.add("user", user, true);
            new ProfilePage(activity);
        });
    }

    public interface Callback {
        void onResponse(Response response) throws IOException, JSONException;
    }
}
