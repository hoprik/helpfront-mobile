package ru.helpfront.base.components.ui;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatButton;
import ru.helpfront.base.R;

public class Button extends AppCompatButton {
    private GradientDrawable backgroundDrawable;
    private ImageView imageView;  // Добавляем ImageView для отображения изображения

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
        // Создаем GradientDrawable для фона кнопки
        backgroundDrawable = new GradientDrawable();
        backgroundDrawable.setShape(GradientDrawable.RECTANGLE);

        int defaultColor = 0xFFFFFFFF;
        float defaultRadius = 0;
        int defaultPadding = 0;

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Button);
            int backgroundColor = a.getColor(R.styleable.Button_backgroundColor, defaultColor);
            float cornerRadius = a.getDimension(R.styleable.Button_cornerRadius, defaultRadius);
            int padding = a.getInt(R.styleable.Button_padding, defaultPadding);
            int paddingX = a.getInt(R.styleable.Button_paddingX, padding);
            int paddingY = a.getInt(R.styleable.Button_paddingY, padding);
            float cornerRadiusLeft = a.getDimension(R.styleable.Button_cornerRadiusLeft, cornerRadius);
            float cornerRadiusTop = a.getDimension(R.styleable.Button_cornerRadiusTop, cornerRadius);
            float cornerRadiusRight = a.getDimension(R.styleable.Button_cornerRadiusRight, cornerRadius);
            float cornerRadiusBottom = a.getDimension(R.styleable.Button_cornerRadiusBottom, cornerRadius);
            Drawable imageDrawable = a.getDrawable(R.styleable.Button_imageSrc);
            a.recycle();

            // Устанавливаем цвет и радиус для кнопки
            backgroundDrawable.setColor(backgroundColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                backgroundDrawable.setPadding(paddingX, paddingY, paddingX, paddingY);
            }
            backgroundDrawable.setCornerRadii(new float[]{
                    cornerRadiusLeft, cornerRadiusTop,
                    cornerRadiusRight, cornerRadiusTop,
                    cornerRadiusRight, cornerRadiusBottom,
                    cornerRadiusLeft, cornerRadiusBottom
            });

            // Устанавливаем размеры текста и фон кнопки
            setTextSize(16);
            setBackground(backgroundDrawable);
            setGravity(Gravity.CENTER);

            // Если изображение задано, добавляем его
            if (imageDrawable != null) {
                setImageDrawableToFitButton(imageDrawable);
            }
        } else {
            backgroundDrawable.setColor(defaultColor);
            backgroundDrawable.setCornerRadius(defaultRadius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                backgroundDrawable.setPadding(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
            }
            backgroundDrawable.setCornerRadii(new float[]{
                    defaultRadius, defaultRadius,
                    defaultRadius, defaultRadius,
                    defaultRadius, defaultRadius,
                    defaultRadius, defaultRadius
            });
        }
    }

    private void setImageDrawableToFitButton(Drawable imageDrawable) {
        setBackground(imageDrawable);
    }
}