package ru.helpfront.base.components.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import androidx.appcompat.widget.AppCompatButton;
import ru.helpfront.base.R;

public class Button extends AppCompatButton {
    private GradientDrawable backgroundDrawable;

    public Button(Context context) {
        super(context);
        init(null);
    }

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Button(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // Создаем GradientDrawable
        backgroundDrawable = new GradientDrawable();
        backgroundDrawable.setShape(GradientDrawable.RECTANGLE);

        // Устанавливаем значения по умолчанию
        int defaultColor = 0xFFFFFFFF; // Белый
        float defaultRadius = 5f; // Радиус закругления по умолчанию

        // Получаем атрибуты из XML
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Button);
            int backgroundColor = a.getColor(R.styleable.Button_backgroundColor, defaultColor);
            float cornerRadius = a.getDimension(R.styleable.Button_cornerRadius, defaultRadius);
            a.recycle();

            // Устанавливаем цвет и радиус
            backgroundDrawable.setColor(backgroundColor);
            backgroundDrawable.setCornerRadius(cornerRadius);
        } else {
            // Устанавливаем значения по умолчанию
            backgroundDrawable.setColor(defaultColor);
            backgroundDrawable.setCornerRadius(defaultRadius);
        }

        setPadding(20,10,20,10);

        setTextSize(16);

        // Устанавливаем фон кнопки
        setBackground(backgroundDrawable);
        // Центрируем текст
        setGravity(Gravity.CENTER);
    }

    // Метод для изменения цвета фона
    public void setCustomBackgroundColor(int color) {
        backgroundDrawable.setColor(color);
    }

}