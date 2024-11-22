package ru.helpfront.base.components.features.MySchool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.Functions;

import java.util.Random;

@SuppressLint("ViewConstructor")
public class UserCart extends ConstraintLayout {
    String name;
    String bottomText;
    String photoUrl;
    private ImageView imageView;
    private TextView textView1;
    private TextView textView2;

    public UserCart(Context context, String name, String bottomText, String photoUrl) {
        super(context);
        this.name = name;
        this.bottomText = bottomText;
        this.photoUrl = photoUrl;
        init(context);
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

    private void init(Context context) {
        imageView = new ImageView(context);
        LayoutParams webViewParams = new LayoutParams(Functions.dpToPx(90, context), LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(webViewParams);

        ConstraintLayout.LayoutParams paramsWebView = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        paramsWebView.startToStart = LayoutParams.PARENT_ID;
        paramsWebView.topToTop = LayoutParams.PARENT_ID;
        imageView.setLayoutParams(paramsWebView);
        int color = getResources().getColor(getRandomColor());
        Glide.with(this)
                .load(photoUrl)
                .transform(new RoundedCorners(100))
                .placeholder(new ColorDrawable(color))
                .error(new ColorDrawable(color))
                .override(90, 90) // Set the desired size
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

        textView1 = new TextView(context);
        textView1.setText(name);
        textView1.setTextColor(getResources().getColor(R.color.white));
        textView1.setTextSize(24);
        LinearLayout.LayoutParams textView1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Functions.dpToPx(30, context));
        textView1.setLayoutParams(textView1Params);
        linearLayout.addView(textView1);

        // Создаем TextView 2
        textView2 = new TextView(context);
        textView2.setText(bottomText);
        textView2.setTextColor(getResources().getColor(R.color.white));
        textView2.setTextSize(16);
        LinearLayout.LayoutParams textView2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Functions.dpToPx(60, context));
        textView2.setLayoutParams(textView2Params);
        linearLayout.addView(textView2);

        setBackgroundColor(getResources().getColor(R.color.secondBg));
        LayoutParams userCartParam = new LayoutParams(LayoutParams.MATCH_PARENT, Functions.dpToPx(90, context));
        userCartParam.setMargins(0,0,0,16);
        setLayoutParams(userCartParam);
    }
}
