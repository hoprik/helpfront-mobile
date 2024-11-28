package ru.helpfront.base.components.features.Sidebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.R;
import ru.helpfront.base.components.features.MySchool.UserCard;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.functions.Network;
import ru.helpfront.base.types.Group;

import java.io.IOException;

@SuppressLint("ViewConstructor")
public class GroupProfile extends ScrollView {

    public GroupProfile(@NonNull @NotNull Context context, String groupId, String nameGroup, String avatar, String slogan, int coins) {
        super(context);
        Network.sendPOST("api/group/getUsers", "{\"id\":\""+groupId+"\"}", Functions.getCookie(), response -> {
            JSONObject object = new JSONObject(response.body().string());
            Log.d("debug", object.toString());
            JSONObject data = object.getJSONObject("data");
            Group group = new Group();
            group.parse(data);
            init(context, group, nameGroup, avatar, slogan, coins);
        });

    }
    
    public void init(Context context, Group group, String nameGroup, String avatar, String slogan, int coins){
        // Создаем основной ConstraintLayout
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        constraintLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.firstBg));
        this.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Создаем ImageView
        ImageView imageView = new ImageView(context);
        imageView.setId(View.generateViewId());
        imageView.setLayoutParams(new ConstraintLayout.LayoutParams(Functions.dpToPx(150, context), Functions.dpToPx(150, context)));
        int color = getResources().getColor(Functions.getRandomColor());
        Glide.with(this)
                .load(avatar)
                .transform(new RoundedCorners(100))
                .placeholder(new ColorDrawable(color))
                .error(new ColorDrawable(color))
                .override(150, 150)
                .into(imageView);
        constraintLayout.addView(imageView);

        // Создаем TextView для заголовка
        TextView titleTextView = new TextView(context);
        titleTextView.setId(View.generateViewId());
        titleTextView.setText(nameGroup);
        titleTextView.setTextSize(32);
        titleTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        titleTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        constraintLayout.addView(titleTextView);

        // Создаем TextView для слогана
        TextView sloganTextView = new TextView(context);
        sloganTextView.setId(View.generateViewId());
        sloganTextView.setText("Слоган: "+slogan);
        sloganTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        sloganTextView.setTextSize(18);
        constraintLayout.addView(sloganTextView);

        // Создаем TextView для "Коинов"
        TextView coinsTextView = new TextView(context);
        coinsTextView.setId(View.generateViewId());
        coinsTextView.setText("Коинов: "+coins);
        coinsTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        coinsTextView.setTextSize(18);
        constraintLayout.addView(coinsTextView);

        // Создаем TextView для "Руководитель"
        TextView leaderTextView = new TextView(context);
        leaderTextView.setId(View.generateViewId());
        leaderTextView.setText("Руководитель");
        leaderTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        leaderTextView.setTextSize(24);
        if (group.getLeader() != null) constraintLayout.addView(leaderTextView);

        // Создаем LinearLayout
        LinearLayout linearLayout1 = new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        linearLayout1.setId(View.generateViewId());
        linearLayout1.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout1.addView(new UserCard(context, group.getLeader(),false));
        if (group.getLeader() != null) constraintLayout.addView(linearLayout1);

        // Создаем TextView для "Староста"
        TextView monitorTextView = new TextView(context);
        monitorTextView.setId(View.generateViewId());
        monitorTextView.setText("Староста");
        monitorTextView.setTextSize(24);
        monitorTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        if (group.getHeadman() != null) constraintLayout.addView(monitorTextView);

        // Создаем второй LinearLayout
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.setId(View.generateViewId());
        linearLayout2.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout2.addView(new UserCard(context, group.getHeadman(),false));
        if (group.getHeadman() != null) constraintLayout.addView(linearLayout2);

        // Создаем TextView для "Ученики"
        TextView studentsTextView = new TextView(context);
        studentsTextView.setId(View.generateViewId());
        studentsTextView.setText("Ученики");
        studentsTextView.setTextSize(24);
        studentsTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        constraintLayout.addView(studentsTextView);


        // Создаем LinearLayout внутри ScrollView
        LinearLayout scrollLinearLayout = new LinearLayout(context);
        scrollLinearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollLinearLayout.setId(View.generateViewId());
        scrollLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        group.getUsers().forEach(object -> scrollLinearLayout.addView(new UserCard(context, object,false)));
        constraintLayout.addView(scrollLinearLayout);

        // Устанавливаем ограничения с помощью ConstraintSet
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        // Устанавливаем ограничения для ImageView
        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 16);
        constraintSet.connect(imageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 16);

        // Устанавливаем ограничения для заголовка
        constraintSet.connect(titleTextView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 16);
        constraintSet.connect(titleTextView.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, 8);

        // Устанавливаем ограничения для слогана
        constraintSet.connect(sloganTextView.getId(), ConstraintSet.TOP, titleTextView.getId(), ConstraintSet.BOTTOM, 8);
        constraintSet.connect(sloganTextView.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, 8);

        // Устанавливаем ограничения для "Коинов"
        constraintSet.connect(coinsTextView.getId(), ConstraintSet.TOP, sloganTextView.getId(), ConstraintSet.BOTTOM, 5);
        constraintSet.connect(coinsTextView.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, 8);

        // Устанавливаем ограничения для "Руководитель"
        constraintSet.connect(leaderTextView.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.BOTTOM, 12);
        constraintSet.connect(leaderTextView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 16);

        // Устанавливаем ограничения для первого LinearLayout
        constraintSet.connect(linearLayout1.getId(), ConstraintSet.TOP, leaderTextView.getId(), ConstraintSet.BOTTOM, 12);
        constraintSet.connect(linearLayout1.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 0);
        constraintSet.connect(linearLayout1.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END, 0);

        // Устанавливаем ограничения для "Староста"
        constraintSet.connect(monitorTextView.getId(), ConstraintSet.TOP, linearLayout1.getId(), ConstraintSet.BOTTOM, 12);
        constraintSet.connect(monitorTextView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 16);

        // Устанавливаем ограничения для второго LinearLayout
        constraintSet.connect(linearLayout2.getId(), ConstraintSet.TOP, monitorTextView.getId(), ConstraintSet.BOTTOM, 16);
        constraintSet.connect(linearLayout2.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 0);
        constraintSet.connect(linearLayout2.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END, 0);

        // Устанавливаем ограничения для "Ученики"
        constraintSet.connect(studentsTextView.getId(), ConstraintSet.TOP, group.getHeadman() != null? linearLayout2.getId(): linearLayout1.getId(), ConstraintSet.BOTTOM, 12);
        constraintSet.connect(studentsTextView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 16);

        // Устанавливаем ограничения для ScrollView
        constraintSet.connect(scrollLinearLayout.getId(), ConstraintSet.TOP, studentsTextView.getId(), ConstraintSet.BOTTOM, 12);
        constraintSet.connect(scrollLinearLayout.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 0);

        // Применяем ограничения
        constraintSet.applyTo(constraintLayout);

        this.addView(constraintLayout);

        new SidePanel(context, this);
    }
}
