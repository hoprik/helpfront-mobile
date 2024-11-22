package ru.helpfront.base.components.features.MySchool;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.DataBank;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.functions.Network;

import java.io.IOException;
import java.util.*;
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
        getGroups().forEach(jsonObject -> {
            try {
                String avatar = jsonObject.getString("avatar");
                String status = jsonObject.getString("status");
                String name = jsonObject.getString("name");
                int number = jsonObject.getInt("number");
                int money = jsonObject.getInt("money");
                if (!avatar.isEmpty()){
                    avatar = "https://www.helpfront.ru" + avatar.split("www")[1];
                }
                if (status.isEmpty()){
                    status = "Не указан";
                }
                String fullName = String.format("%s %s", name, number);
                this.addView(renderGroup(context, fullName, status, avatar, money));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private int getRandomColor(){
        Random rand = new Random();
        int n = rand.nextInt(100);
        n+=1;
        if (n < 26){
            return R.color.red;
        }
        if (n > 26 && n < 51){
            return R.color.green;
        }
        if (n > 51 && n < 76){
            return R.color.yellow;
        }
        if (n > 76 && n < 100){
            return R.color.blue;
        }
        return R.color.red;
    }

    private ConstraintLayout renderGroup(Context context, String name, String slogan, String avatar, int money){
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
        ConstraintLayout.LayoutParams paramsImageView = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        paramsImageView.setMargins(Functions.dpToPx(8, context), Functions.dpToPx(8, context), 0, 0);
        imageView.setLayoutParams(paramsImageView);
        int color = getResources().getColor(getRandomColor());
        Glide.with(this)
                .load(avatar)
                .transform(new RoundedCorners(100))
                .placeholder(new ColorDrawable(color))
                .error(new ColorDrawable(color))
                .override(90, 90)
                .into(imageView);
        constraintLayout.addView(imageView);

        // Создаем TextView для имени группы
        TextView groupName = new TextView(context);
        groupName.setId(View.generateViewId());
        groupName.setText(name);
        groupName.setTextSize(26);
        groupName.setTextColor(ContextCompat.getColor(context, R.color.white));
        groupName.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        constraintLayout.addView(groupName);

        // Создаем TextView для слогана
        TextView groupSlogan = new TextView(context);
        groupSlogan.setId(View.generateViewId());
        groupSlogan.setText("Слоган: "+slogan);
        groupSlogan.setTextSize(15);
        groupSlogan.setTextColor(ContextCompat.getColor(context, R.color.white));
        groupSlogan.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        constraintLayout.addView(groupSlogan);

        // Создаем TextView для денег
        TextView groupMoney = new TextView(context);
        groupMoney.setId(View.generateViewId());
        groupMoney.setText(String.valueOf(money));
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
        constraintSet.connect(groupMoney.getId(), ConstraintSet.START, imageView2.getId(), ConstraintSet.END, Functions.dpToPx(4, context));

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

    private Collection<JSONObject> getGroups() {
        Map<String, JSONObject> users = (Map<String, JSONObject>) DataBank.get("groups");
        return users.values();
    }
}
