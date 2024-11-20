package ru.helpfront.base.features;

import android.app.Application;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import ru.helpfront.base.R;

public class Navbar extends LinearLayout {
    private TextView textView;
    private Button button;

    public Navbar(Context context) {
        super(context);
        init(context);
    }

    public Navbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Navbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Navbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setBackgroundResource(R.color.secondBg);
        this.setOrientation(HORIZONTAL);
        this.setPadding(16, 16, 16, 16);

        // Создание и настройка TextView
        textView = new TextView(context);
        textView.setText("Здесь будет текст");
        textView.setTextSize(16);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        // Создание и настройка Button
        button = new Button(context);
        button.setText("Нажми меня");
        button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        // Установка обработчика нажатия на кнопку
        button.setOnClickListener(v -> textView.setText("Кнопка нажата!"));

        // Добавление элементов в LinearLayout
        this.addView(textView);
        this.addView(button);
    }

    public void setText(String text) {
        textView.setText(text);
    }
}