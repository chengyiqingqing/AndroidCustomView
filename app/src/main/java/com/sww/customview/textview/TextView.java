package com.sww.customview.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sww.customview.R;

/**
 * initAttribute(context,attrs); // 初始化属性
 * initView();  // 给view设置个值
 * onMeasure(); // 测量 --》设置宽高；
 * onDraw(); // 绘制 --》 绘制view;
 */
public class TextView extends View {

    private String mText;
    // R.color.colorAccent xml文件
    // Color.Black Java代码
    private int mTextColor = Color.BLACK;
    private int mTextSize = sp2px(15);
    private Paint mPaint;

    /**
     * 用于在Java代码里直接new
     */
    public TextView(Context context) {
        this(context,null);
    }

    /**
     * 用于在xml文件里做布局组件（可以设置自定义属性）
     */
    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    /**
     * 用于在xml文件里做布局组件（可以设置自定义属性和style样式）
     */
    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context,attrs);
        initView();
    }

    public void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextView);
        mText = typedArray.getString(R.styleable.TextView_swwText);
        mTextColor = typedArray.getColor(R.styleable.TextView_swwTextColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextView_swwTextSize, sp2px(15));
        typedArray.recycle();
    }

    private void initView() {
        mPaint = new Paint();
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 设置字体大小和颜色
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        // 确实是ViewGroup(包含子ViewGroup)不设置北京和这个东西，是不能调用onDraw方法的
        setBackgroundColor(Color.BLACK);
//        setWillNotDraw(false);
    }

    private void initData(){
        setWillNotDraw(false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    /**
     * 自定义View的测量方法
     * @param widthMeasureSpec 由父控件传递过来的值。测量模式和测量大小 的合并值。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 1.获取宽高的模式，宽度和高度
        int widthModel =  MeasureSpec.getMode(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        // 2.判断模式，设置真正的宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthModel == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else if (widthModel == MeasureSpec.AT_MOST) {// wrap_content才需要计算
            // 计算的宽度 与 字体的长度和大小有关  使用画笔Paint来设置
            Rect bounds = new Rect();
            // 测量获取文本的rect
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            width = bounds.width() + getPaddingLeft() + getPaddingRight();
        }
        if (heightModel == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else if (heightModel == MeasureSpec.AT_MOST) {// wrap_content才需要计算
            // 计算的宽度 与 字体的长度和大小有关  使用画笔Paint来设置
            Rect bounds = new Rect();
            // 测量获取文本的rect
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            height = bounds.height() + getPaddingTop() + getPaddingBottom();
        }
        // 3.设置控件的宽高
        setMeasuredDimension(width,height);
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画文本
//        canvas.drawText();
        // 画圆弧
//        canvas.drawArc();
        // 画圆
//        canvas.drawCircle();
        // x 为  开始的位置
        // y 为 baseline; (fontMetricsInt.top为负，baseline为0，bottom为正)
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        Log.e(TAG, "onDraw: " +fontMetricsInt.top );
        Log.e(TAG, "onDraw: " +fontMetricsInt.bottom );
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 -fontMetricsInt.bottom;
        int baseLine = getHeight()/2 +dy;
        int x = getPaddingLeft();
        canvas.drawText(mText, x , baseLine, mPaint);
    }

    private static final String TAG = "TextView";

    /**
     * 处理跟用户交互的东西，手指触摸
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.onTouchEvent(event);
    }

    private int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

}
