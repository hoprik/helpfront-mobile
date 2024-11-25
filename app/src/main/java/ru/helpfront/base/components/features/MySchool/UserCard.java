package ru.helpfront.base.components.features.MySchool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import org.json.JSONObject;
import ru.helpfront.base.R;
import ru.helpfront.base.components.features.Sidebar.UserProfile;
import ru.helpfront.base.functions.DataBank;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.types.User;

import java.util.Map;
import java.util.Random;

@SuppressLint("ViewConstructor")
public class UserCard extends ConstraintLayout {
    public UserCard(Context context, JSONObject object, Boolean isTeacher) {
        super(context);
        User user = new User();
        user.parse(object);
        init(context, user, isTeacher);
    }


    private void init(Context context, User user, Boolean isTeacher) {
        ImageView imageView = new ImageView(context);
        LayoutParams webViewParams = new LayoutParams(Functions.dpToPx(90, context), LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(webViewParams);

        ConstraintLayout.LayoutParams paramsWebView = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        paramsWebView.startToStart = LayoutParams.PARENT_ID;
        paramsWebView.topToTop = LayoutParams.PARENT_ID;
        imageView.setLayoutParams(paramsWebView);
        int color = getResources().getColor(Functions.getRandomColor());
        Glide.with(this)
                .load(user.getAvatar())
                .transform(new RoundedCorners(100))
                .placeholder(new ColorDrawable(color))
                .error(new ColorDrawable(color))
                .override(90, 90)
                .into(imageView);
        addView(imageView);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams linearLayoutParams = new LayoutParams(Functions.dpToPx(255, context), Functions.dpToPx(90, context));
        linearLayout.setLayoutParams(linearLayoutParams);
        // Устанавливаем ограничения для LinearLayout
        ConstraintLayout.LayoutParams paramsLinearLayout = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
        paramsLinearLayout.endToEnd = LayoutParams.PARENT_ID;
        paramsLinearLayout.topToTop = LayoutParams.PARENT_ID;
        paramsLinearLayout.startToEnd = imageView.getId();
        paramsLinearLayout.bottomToBottom = LayoutParams.PARENT_ID;
        linearLayout.setLayoutParams(paramsLinearLayout);
        addView(linearLayout);

        TextView textView1 = new TextView(context);
        textView1.setText(user.getName()+" "+user.getSurname());
        textView1.setTextColor(getResources().getColor(R.color.white));
        textView1.setTextSize(24);
        LinearLayout.LayoutParams textView1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Functions.dpToPx(30, context));
        textView1.setLayoutParams(textView1Params);
        linearLayout.addView(textView1);

        // Создаем TextView 2
        TextView textView2 = new TextView(context);
        textView2.setText(isTeacher? user.getGroupsName() : user.getLogin());
        textView2.setTextColor(getResources().getColor(R.color.white));
        textView2.setTextSize(16);
        LinearLayout.LayoutParams textView2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Functions.dpToPx(60, context));
        textView2.setLayoutParams(textView2Params);
        linearLayout.addView(textView2);

        setBackgroundColor(getResources().getColor(R.color.secondBg));
        LayoutParams userCartParam = new LayoutParams(LayoutParams.MATCH_PARENT, Functions.dpToPx(90, context));
        userCartParam.setMargins(0,0,0,16);
        setLayoutParams(userCartParam);
        this.setOnClickListener(openSideBar(user));
    }

    private View.OnClickListener openSideBar(User user){
        return v -> new UserProfile(user);
    }
}
