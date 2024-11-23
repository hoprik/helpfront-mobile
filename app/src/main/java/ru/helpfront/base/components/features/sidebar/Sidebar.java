package ru.helpfront.base.components.features.sidebar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.activity.ComponentActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import ru.helpfront.base.R;
import ru.helpfront.base.pages.Page;

public class Sidebar extends ConstraintLayout {
    public Sidebar(Context context, View view) {
        super(context);
        init(context, view);
    }

    private void init(Context context, View homeView) {
        // Создаем основной контейнер
        ConstraintLayout layout = new ConstraintLayout(context);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams);
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.firstBg));

        // Проверка, что homeView не равен null
        if (homeView == null) {
            Log.e("SidebarInit", "homeView is null!");
            return;
        }

        // Убедитесь, что homeView имеет уникальный ID
        homeView.setId(View.generateViewId());
        homeView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));

        // Добавляем homeView в основной макет
        layout.addView(homeView);
        Log.d("SidebarInit", "homeView added to layout");

        // Добавление кнопки закрытия
        ImageView closeButton = new ImageView(context);
        closeButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel); // Убедитесь, что у вас есть иконка закрытия
        closeButton.setId(View.generateViewId()); // Устанавливаем уникальный ID для кнопки

        // Устанавливаем параметры для кнопки закрытия
        ConstraintLayout.LayoutParams closeButtonParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        closeButtonParams.setMargins(16, 16, 16, 0); // Задаем отступы
        closeButton.setLayoutParams(closeButtonParams);

        // Добавляем кнопку закрытия в основной макет
        layout.addView(closeButton);

        // Устанавливаем ограничения для кнопки закрытия
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        constraintSet.connect(closeButton.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
        constraintSet.connect(closeButton.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END);
        constraintSet.applyTo(layout);

        // Устанавливаем ограничения для homeView
        constraintSet.connect(homeView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
        constraintSet.connect(homeView.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END);
        constraintSet.connect(homeView.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(homeView.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START);
        constraintSet.applyTo(layout);

        // Устанавливаем обработчик клика для кнопки закрытия
        closeButton.setOnClickListener(v -> {
            // Закрыть Sidebar или выполнить другое действие
            ((ViewGroup) layout.getParent()).removeView(layout); // Удаляем Sidebar из родительского ViewGroup
        });

        // Добавляем layout в родительский ViewGroup
        ComponentActivity activity = Page.currentPage.activity;
        ViewGroup parentLayout = activity.findViewById(R.id.profileLayout);

        // Проверка, действительно ли родительский Layout существует
        if (parentLayout != null) {
            parentLayout.addView(layout);
            Log.d("SidebarInit", "Sidebar added to parent layout");
        } else {
            Log.e("SidebarInit", "Parent layout not found!");
        }
    }
}
