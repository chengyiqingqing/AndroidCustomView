package com.sww.customview.multicolorTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.sww.customview.R;

public class MultiColorTextView extends AppCompatTextView {

    private int mOriginColor = Color.BLUE;
    private int mChangeColor = Color.RED;

    private Paint mOriginPaint;
    private Paint mChangePaint;

    private float mCurrentProgressRatio = 0.5f;

    public MultiColorTextView(Context context) {
        this(context,null);
    }

    public MultiColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(context,attrs);
        initView();
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiColorTextView);
        mOriginColor = typedArray.getColor(R.styleable.MultiColorTextView_swwOriginColor,mOriginColor);
        mChangeColor = typedArray.getColor(R.styleable.MultiColorTextView_swwChangeColor,mChangeColor);
        typedArray.recycle();
    }


    private void initView() {
        mOriginPaint = new Paint();
        mOriginPaint.setColor(mOriginColor);
        mOriginPaint.setTextSize(getTextSize());
        mChangePaint = new Paint();
        mChangePaint.setColor(mChangeColor);
        mChangePaint.setTextSize(getTextSize());
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        changeTextColor(canvas,mOriginPaint,0, mCurrentProgressRatio);
        changeTextColor(canvas,mChangePaint, mCurrentProgressRatio,1);
    }

    public void changeTextColor(Canvas canvas, Paint paint, float startRatio, float endRatio) {
        canvas.save();
        String content = (String) getText();
        Rect rectBounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), rectBounds);
        // 这里字体的高度和控件的高度是不一致的。
        Rect rect = new Rect((int) (rectBounds.width() * startRatio),
                0,
                (int) (rectBounds.width() * endRatio),
                getHeight());
        canvas.clipRect(rect);
        int x = getWidth() / 2 - rectBounds.width() / 2;
        canvas.drawText(content, x, getBaseLine(paint), paint);
        canvas.restore();
    }

    public int getBaseLine(Paint paint) {
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        return baseLine;
    }

    public void setCurrentProgressRatio(float currentProgressRatio) {
        this.mCurrentProgressRatio = currentProgressRatio;
        invalidate();
    }
}
