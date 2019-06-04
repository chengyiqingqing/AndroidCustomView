package com.sww.customview.textview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MarqueeView extends AppCompatTextView {

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 焦点
    @Override
    public boolean isFocused() {
        return true;
    }

}
