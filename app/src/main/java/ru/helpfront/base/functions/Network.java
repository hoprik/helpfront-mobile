package ru.helpfront.base.functions;

import android.os.Handler;
import android.os.Looper;
import okhttp3.*;

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
                e.printStackTrace();
                handler.post(() -> callback.onFailure(e));

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.post(() -> callback.onResponse(response));
            }
        });
    }

    public interface Callback {
        void onFailure(IOException e);
        void onResponse(Response response);
    }
}
