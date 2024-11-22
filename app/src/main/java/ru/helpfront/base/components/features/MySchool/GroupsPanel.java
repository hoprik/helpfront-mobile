package ru.helpfront.base.components.features.MySchool;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.functions.Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GroupsPanel extends LinearLayout{
    public GroupsPanel(Context context) {
        super(context);
        init(context);
    }

    public GroupsPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GroupsPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setOrientation(VERTICAL);
        this.setPadding(16, 16, 16, 16);
        this.addView(renderGroup(context));
        this.addView(renderGroup(context));
        this.addView(renderGroup(context));
        this.addView(renderGroup(context));

    }

    private CompletableFuture<List<JSONObject>> getGroups() {
        CompletableFuture<List<JSONObject>> future = new CompletableFuture<>();

        Network.sendPOST("api/group/getList", "{}", Functions.getCookie(), new Network.Callback() {
            @Override
            public void onFailure(IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException, JSONException {
                List<JSONObject> groups = new ArrayList<>();
                JSONObject object = new JSONObject(response.body().string());
                if (!object.has("data")) {
                    return;
                }
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject group = array.getJSONObject(i);
                    groups.add(group);
                }
                future.complete(groups);
            }
        });

        return future;
    }

    private ConstraintLayout renderGroup(Context context){
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                Functions.dpToPx(180, context));
        params.setMargins(0,0,0, 16);
        constraintLayout.setLayoutParams(params);
        // Устанавливаем фон
        constraintLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.secondBg));

        // Создаем ImageView для изображения
        ImageView imageView = new ImageView(context);
        imageView.setId(View.generateViewId());
        imageView.setLayoutParams(new ConstraintLayout.LayoutParams(Functions.dpToPx(90, context), Functions.dpToPx(90, context)));
        imageView.setImageResource(R.drawable.ai);
        ConstraintLayout.LayoutParams paramsImageView = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        paramsImageView.setMargins(Functions.dpToPx(8, context), Functions.dpToPx(8, context), 0, 0);
        imageView.setLayoutParams(paramsImageView);
        constraintLayout.addView(imageView);

        // Создаем TextView для имени группы
        TextView groupName = new TextView(context);
        groupName.setId(View.generateViewId());
        groupName.setText("Profi 1");
        groupName.setTextSize(26);
        groupName.setTextColor(ContextCompat.getColor(context, R.color.white));
        groupName.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        constraintLayout.addView(groupName);

        // Создаем TextView для слогана
        TextView groupSlogan = new TextView(context);
        groupSlogan.setId(View.generateViewId());
        groupSlogan.setText("Слоган: ");
        groupSlogan.setTextSize(15);
        groupSlogan.setTextColor(ContextCompat.getColor(context, R.color.white));
        groupSlogan.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        constraintLayout.addView(groupSlogan);

        // Создаем TextView для денег
        TextView groupMoney = new TextView(context);
        groupMoney.setId(View.generateViewId());
        groupMoney.setText("250");
        groupMoney.setTextSize(15);
        groupMoney.setTextColor(ContextCompat.getColor(context, R.color.white));
        groupMoney.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        constraintLayout.addView(groupMoney);

        // Создаем ImageView для иконки денег
        ImageView imageView2 = new ImageView(context);
        imageView2.setId(View.generateViewId());
        imageView2.setLayoutParams(new ConstraintLayout.LayoutParams(Functions.dpToPx(20, context), Functions.dpToPx(20, context)));
        imageView2.setImageResource(R.drawable.money);
        constraintLayout.addView(imageView2);

        // Создаем TextView для участников
        TextView textView4 = new TextView(context);
        textView4.setId(View.generateViewId());
        textView4.setText("Участники: 11");
        textView4.setTextSize(15);
        textView4.setTextColor(ContextCompat.getColor(context, R.color.white));
        textView4.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        constraintLayout.addView(textView4);

        // Создаем LinearLayout для изображений участников
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setId(View.generateViewId()); // Убедитесь, что у LinearLayout есть ID
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(Functions.dpToPx(367, context), Functions.dpToPx(48, context)));
        constraintLayout.addView(linearLayout);

        // Создаем ImageView для первого участника
        ImageView imageView3 = new ImageView(context);
        imageView3.setId(View.generateViewId());
        imageView3.setLayoutParams(new LinearLayout.LayoutParams(Functions.dpToPx(40, context), Functions.dpToPx(40, context)));
        imageView3.setImageResource(R.drawable.duck_icon);
        linearLayout.addView(imageView3);

        // Создаем ImageView для второго участника
        ImageView imageView4 = new ImageView(context);
        imageView4.setId(View.generateViewId());
        imageView4.setLayoutParams(new LinearLayout.LayoutParams(Functions.dpToPx(40, context), Functions.dpToPx(40, context)));
        imageView4.setImageResource(R.drawable.duck_icon);
        linearLayout.addView(imageView4);

        // Устанавливаем ограничения для элементов
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, Functions.dpToPx(8, context));
        constraintSet.connect(imageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, Functions.dpToPx(8, context));

        constraintSet.connect(groupName.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, Functions.dpToPx(8, context));
        constraintSet.connect(groupName.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, Functions.dpToPx(12, context));

        constraintSet.connect(groupSlogan.getId(), ConstraintSet.TOP, groupName.getId(), ConstraintSet.BOTTOM, Functions.dpToPx(6, context));
        constraintSet.connect(groupSlogan.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, Functions.dpToPx(12, context));

        constraintSet.connect(groupMoney.getId(), ConstraintSet.TOP, groupSlogan.getId(), ConstraintSet.BOTTOM, Functions.dpToPx(8, context));
        constraintSet.connect(groupMoney.getId(), ConstraintSet.START, imageView2.getId(), ConstraintSet.END, Functions.dpToPx(8, context));

        constraintSet.connect(imageView2.getId(), ConstraintSet.TOP, groupSlogan.getId(), ConstraintSet.BOTTOM, Functions.dpToPx(8, context));
        constraintSet.connect(imageView2.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, Functions.dpToPx(12, context));

        constraintSet.connect(textView4.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.BOTTOM, Functions.dpToPx(4, context));
        constraintSet.connect(textView4.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, Functions.dpToPx(8, context));

        constraintSet.connect(linearLayout.getId(), ConstraintSet.TOP, textView4.getId(), ConstraintSet.BOTTOM, Functions.dpToPx(1, context));
        constraintSet.connect(linearLayout.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 0);
        constraintSet.connect(linearLayout.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END, 0);

        constraintSet.applyTo(constraintLayout);

        // Устанавливаем созданный ConstraintLayout как содержимое активности
        return constraintLayout;
    }
}
