package ru.helpfront.base.components.features.sidebar;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import org.jetbrains.annotations.NotNull;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.Functions;

import java.util.Random;

public class UserProfile {
    public UserProfile(ComponentActivity context, String name, String group, String avatar, String role, String birthday, String doneTask, String likes, String coins, String collectables) {
        init(context, name, group, avatar, role, birthday, doneTask, likes, coins, collectables);
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

    private void init(ComponentActivity context, String name, String group, String avatar, String role, String birthday, String doneTask, String likes, String coins, String collectables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View homeView = inflater.inflate(R.layout.userui, null);
        ((TextView) homeView.findViewById(R.id.profileUserName)).setText(name);
        ((ImageView) homeView.findViewById(R.id.profileBirthdayIcon)).setImageResource(R.drawable.birthday);
        ((TextView) homeView.findViewById(R.id.profileBirthdayName)).setText(birthday);
        ((TextView) homeView.findViewById(R.id.profileClassAndRole)).setText("Класс: "+group+" Роль: "+role);
        ImageView avatarView = homeView.findViewById(R.id.imageView);
        int color = homeView.getResources().getColor(getRandomColor());
        Glide.with(context)
                .load(avatar)
                .transform(new RoundedCorners(100))
                .placeholder(new ColorDrawable(color))
                .error(new ColorDrawable(color))
                .override(140, 140)
                .into(avatarView);
        ((TextView) homeView.findViewById(R.id.textView7)).setText(doneTask);
        ((TextView) homeView.findViewById(R.id.textView9)).setText(likes);
        ((TextView) homeView.findViewById(R.id.textView11)).setText(coins);
        ((TextView) homeView.findViewById(R.id.textView13)).setText(collectables);

        new Sidebar(context, homeView);
    }

}
