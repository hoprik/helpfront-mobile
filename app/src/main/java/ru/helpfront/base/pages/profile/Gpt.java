package ru.helpfront.base.pages.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import io.noties.markwon.Markwon;
import io.noties.markwon.core.MarkwonTheme;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.helpfront.base.R;
import ru.helpfront.base.functions.DataBank;
import ru.helpfront.base.functions.Functions;
import ru.helpfront.base.functions.Network;
import ru.helpfront.base.pages.Page;
import ru.helpfront.base.types.User;

import java.io.IOException;

public class Gpt extends Page {
    JSONArray array = new JSONArray();
    public Gpt(ComponentActivity activity, int renderId) {
        super(activity, renderId);
    }

    @Override
    public void render() {
        Functions.changeLayout(this.activity, R.layout.gpt, renderId);
        activity.findViewById(R.id.sendButton).setOnClickListener(gptSend());
    }

    private View.OnClickListener gptSend(){
        return v -> {
            try {
                ((EditText)activity.findViewById(R.id.textView4)).setText("");
                String question = ((EditText)activity.findViewById(R.id.textView4)).getText().toString();
                if (question.isEmpty()){
                    return;
                }
                JSONObject message = new JSONObject();
                message.put("role", "user");
                message.put("content", question);
                array.put(message);
                LinearLayout messages = activity.findViewById(R.id.messages);
                messages.addView(addMessage(question, false));
                JSONObject gptMessages = new JSONObject();
                gptMessages.put("messages", array);
                Network.sendPOST("api/gpt", gptMessages.toString(), "", response -> {
                    String json = response.body().string();
                    JSONObject data = new JSONObject(json);
                    if (data.has("data")){
                        String gptMessage = data.getString("data");
                        messages.addView(addMessage(gptMessage, true));
                        JSONObject gptMessageJson = new JSONObject();
                        message.put("role", "assistant");
                        message.put("content", gptMessage);
                        array.put(gptMessageJson);
                    }
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @SuppressLint("ResourceAsColor")
    private ConstraintLayout addMessage(String message, boolean isGpt){
        ConstraintLayout messageWrapper = new ConstraintLayout(activity);
        ConstraintLayout.LayoutParams messageWrapperParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        messageWrapperParams.setMargins(0, 10, 0,0);
        messageWrapper.setLayoutParams(messageWrapperParams);
        messageWrapper.setId(View.generateViewId());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.weight = Math.min(params.weight, Functions.dpToPx(250, activity));
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setId(View.generateViewId());
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(R.color.secondBg);

        TextView userMessage = new TextView(activity);
        userMessage.setText(!isGpt? ((User) DataBank.get("user")).getName() : Functions.getText(R.string.gpt));
        userMessage.setTextColor(activity.getColor(R.color.white));
        userMessage.setTextSize(26);
        userMessage.setId(View.generateViewId());
        userMessage.setTextAlignment(isGpt ? View.TEXT_ALIGNMENT_TEXT_END: View.TEXT_ALIGNMENT_TEXT_START);

        TextView messageView = new TextView(activity);
        messageView.setTextColor(activity.getColor(R.color.white));
        messageView.setId(View.generateViewId());
        messageView.setTextSize(20);
        Markwon markwon = Markwon.create(activity);
        markwon.setMarkdown(messageView, message);

        linearLayout.addView(userMessage);
        linearLayout.addView(messageView);
        messageWrapper.addView(linearLayout);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(messageWrapper);

        if (isGpt) {
            constraintSet.connect(linearLayout.getId(), ConstraintSet.TOP, messageWrapper.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(linearLayout.getId(), ConstraintSet.END, messageWrapper.getId(), ConstraintSet.END, 0);
            constraintSet.connect(linearLayout.getId(), ConstraintSet.BOTTOM, messageWrapper.getId(), ConstraintSet.BOTTOM, 0);
        } else {
            constraintSet.connect(linearLayout.getId(), ConstraintSet.TOP, messageWrapper.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(linearLayout.getId(), ConstraintSet.START, messageWrapper.getId(), ConstraintSet.START, 0);
            constraintSet.connect(linearLayout.getId(), ConstraintSet.BOTTOM, messageWrapper.getId(), ConstraintSet.BOTTOM, 0);
        }

        constraintSet.applyTo(messageWrapper);



        return messageWrapper;
    }

}
