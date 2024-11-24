package ru.helpfront.base.components.features.Sidebar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import ru.helpfront.base.MainActivity;
import ru.helpfront.base.R;
import ru.helpfront.base.types.User;

import java.time.ZonedDateTime;
import java.util.Random;

public class UserProfile {
    public UserProfile(User user) {
        init(MainActivity.activity, user.getName()+" "+user.getSurname(), user.getGroupsName(), user.getAvatar(), user.getRole(), ZonedDateTime.parse(user.getBirthday()), user.getCompletedTasks(), user.getThanks(), user.getMoney(), "0");
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

    private void init(ComponentActivity context, String name, String group, String avatar, String role, ZonedDateTime birthday, int doneTask, int likes, int coins, String collectables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View homeView = inflater.inflate(R.layout.userprofile, null);
        ((TextView) homeView.findViewById(R.id.profileUserName)).setText(name);
        ((ImageView) homeView.findViewById(R.id.profileBirthdayIcon)).setImageResource(R.drawable.birthday);
        ((TextView) homeView.findViewById(R.id.profileBirthdayName)).setText(String.format("%02d.%02d.%04d", birthday.getDayOfMonth(), birthday.getMonth().getValue(), birthday.getYear()));
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
        ((TextView) homeView.findViewById(R.id.textView7)).setText(String.valueOf(doneTask));
        ((TextView) homeView.findViewById(R.id.textView9)).setText(String.valueOf(likes));
        ((TextView) homeView.findViewById(R.id.textView11)).setText(String.valueOf(coins));
        ((TextView) homeView.findViewById(R.id.textView13)).setText(collectables);

        new SidePanel(context, homeView);
    }

}
