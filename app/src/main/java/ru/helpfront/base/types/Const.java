package ru.helpfront.base.types;

import android.graphics.Color;
import android.text.TextPaint;
import io.noties.markwon.core.MarkwonTheme;
import ru.helpfront.base.MainActivity;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.Functions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Const {
    public static int MIN_LENGTH_LOGIN = 5;
    public static int MIN_LENGTH_PASSWORD = 6;
    public static int MAX_LENGTH_LOGIN = 15;
    public static Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+", Pattern.CASE_INSENSITIVE);
    public static Pattern RUS_WORD_CASE_INSENSITIVE_PATTERN = Pattern.compile("[А-я][а-яё]*$", Pattern.CASE_INSENSITIVE);
    public static Pattern RUS_WORD_PATTERN = Pattern.compile("^[А-Я][а-яё]*$", Pattern.CASE_INSENSITIVE);
    public static Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE);
    public static Pattern LOGIN_PATTERN = Pattern.compile(
            String.format("^[a-zA-Z0-9а-яА-Я-_ ]{%d,%d}$", MIN_LENGTH_LOGIN, MAX_LENGTH_LOGIN),
            Pattern.CASE_INSENSITIVE
    );
    public static final Map<Roles, String> ROLE_RUS = new HashMap<>();

    static {
        ROLE_RUS.put(Roles.ADMIN, Functions.getText(R.string.admin));
        ROLE_RUS.put(Roles.GUEST, Functions.getText(R.string.guest));
        ROLE_RUS.put(Roles.STUDENT, Functions.getText(R.string.student));
        ROLE_RUS.put(Roles.TEACHER, Functions.getText(R.string.teacher));
    }
    public enum Roles {
        GUEST,
        STUDENT,
        TEACHER,
        ADMIN
    }


}
