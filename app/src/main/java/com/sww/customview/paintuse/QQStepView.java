package com.sww.customview.paintuse;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.sww.customview.R;

/**
 * 1.分析效果
 * 2.确定自定义属性，编写attrs.xml
 * 3.在布局中使用
 * 4.在自定义View中获取自定义属性
 * 5.在onMeasure()
 * 6.画外圆弧，内圆弧，文字
 * 7.其他，动态更新，重新绘制
 */
public class QQStepView extends View {

    private int mOutColor = Color.BLUE;
    private int mInnerColor = Color.RED;
    private int mBorderWidth = 20;
    private int mStepTextSize = 20;
    private int mStepTextColor = Color.RED;

    private Paint mOutPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;

    private int mStepMax = 360;// 总共的
    private int mCurrentStep = 60;// 当前步数；


    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 1.分析效果
        // 2.确定自定义属性，编写attrs.xml
        // 3.在布局中使用
        // 4.在自定义View中获取自定义属性
        initAttribute(context,attrs);
        initView();
        // 5.在onMeasure()
        // 6.画外圆弧，内圆弧，文字
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOutColor = typedArray.getColor(R.styleable.QQStepView_outerColor,mOutColor);
        mInnerColor = typedArray.getColor(R.styleable.QQStepView_innerColor,mInnerColor);
        mBorderWidth = typedArray.getDimensionPixelOffset(R.styleable.QQStepView_borderWidth,mBorderWidth);
        mStepTextSize = typedArray.getDimensionPixelSize(R.styleable.QQStepView_swwStepTextSize,mStepTextSize);
        mStepTextColor = typedArray.getColor(R.styleable.QQStepView_swwStepTextColor,mStepTextColor);
        typedArray.recycle();
    }

    private void initView() {
        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);// 设置弧边框的宽度
        mOutPaint.setColor(mOutColor);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mInnerColor);
        mTextPaint.setTextSize(mStepTextSize);
        mTextPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 甭管是wrap还是精确值。宽高不一致，取最小值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // 这里设置完宽高之后，在onDraw()方法中获取的就是getWidth()，getHeight就可以直接用
        setMeasuredDimension(width > height ? height : width,
                width > height ? height : width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 分层：底部背景圆弧，动效圆弧，数字
        // 第一层：底部背景圆弧：（1）分析坐标系，设置画笔，设置绘制区域，画布绘制图形
        // TODO: 思考一下为什么是mBorder/2 2019/6/2
        RectF rectF = new RectF(mBorderWidth/2,
                mBorderWidth/2,
                getWidth() - mBorderWidth/2 ,
                getWidth() - mBorderWidth/2);
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);
        // 第二层：画内圆弧；
        float sweepAngle = mCurrentStep * 270 / mStepMax ;
        canvas.drawArc(rectF,135,sweepAngle,false,mInnerPaint);

        // 第三层：画文字；
        String currentStep = String.valueOf(mCurrentStep);
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(currentStep, 0, currentStep.length(), textBounds);
        int dx = getWidth()/2 - textBounds.width()/2;
        // 基线 baseline
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom- fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 +dy;
        canvas.drawText(currentStep, dx, baseLine, mTextPaint);
    }

    public void setStepMax(int stepMax) {
        this.mStepMax = stepMax;
    }

    public void setCurrentStep(int currentStep) {
        this.mCurrentStep = currentStep;
        invalidate();
    }

}
