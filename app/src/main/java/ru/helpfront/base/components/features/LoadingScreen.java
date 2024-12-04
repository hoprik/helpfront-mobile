package ru.helpfront.base.components.features;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import ru.helpfront.base.MainActivity;
import ru.helpfront.base.R;

public class LoadingScreen extends FrameLayout {

    public LoadingScreen(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LoadingScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingScreen(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Загружаем layout
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.loading, this, true);

        // Убедимся, что layout занимает весь экран
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        ImageView imageView = findViewById(R.id.imageView2);

        // Загружаем изображение (графику или GIF) с помощью Glide
        Glide.with(this)
                .load(R.drawable.duckloading)  // Или URL: .load("https://example.com/yourgif.gif")
                .into(imageView);

    }
}
