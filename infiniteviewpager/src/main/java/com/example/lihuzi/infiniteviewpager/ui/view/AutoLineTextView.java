package com.example.lihuzi.infiniteviewpager.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AutoLineTextView extends AppCompatTextView {

    private float textSize;
    private float heightPadding;

    private boolean showAll;

    private int limitLine = 3;
    private Runnable refresh = () -> {
        int line = this.getLineCount();
        if (line > 3) {
            if (showAll) {
                this.setMaxLines(Integer.MAX_VALUE);
            } else {
                this.setMaxLines(limitLine);
            }
            invalidate();
        } else {
        }
    };

    public AutoLineTextView(Context context) {
        super(context);
    }

    public AutoLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        setOnClickListener(v -> {
            showAll = !showAll;
            post(refresh);
        });
    }

    public AutoLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void changeState(boolean state) {
        showAll = state;
        post(refresh);
    }

    public boolean getState() {
        return showAll;
    }

    public void setText(String text) {
        super.setText(text);
        this.post(refresh);
    }
}
