package com.sww.customview.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sww.customview.R;

public class RatingBarView extends View {

    private Bitmap mStarNormalBitmap;
    private Bitmap mStarFocusBitmap;
    private int mStarNumber = 5;

    private int mCurrentProgress ;

    public RatingBarView(Context context) {
        this(context,null);
    }

    public RatingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(context,attrs);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        int starNormalId = typedArray.getResourceId(R.styleable.RatingBarView_starNormal,0);
        if (starNormalId == 0) throw new RuntimeException("必须设置 starNormal 属性");
        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(),starNormalId);
        int starFocusId = typedArray.getResourceId(R.styleable.RatingBarView_starFocus,0);
        if (starFocusId == 0) throw new RuntimeException("必须设置 starFocusId 属性");
        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(),starFocusId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = mStarNormalBitmap.getHeight();
        int width = mStarNormalBitmap.getWidth()*mStarNumber;
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int index = 0; index < mStarNumber; index++) {
            int left = index*mStarNormalBitmap.getWidth();
            if (index > mCurrentProgress) {
                canvas.drawBitmap(mStarNormalBitmap, left, 0, null);
            } else {
                canvas.drawBitmap(mStarFocusBitmap, left, 0, null);
            }
        }
    }

    private static final String TAG = "RatingBarView";
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 移动 按下 抬起 处理逻辑都是一样，判断手指的位置，根据当前位置计算出分数，再去刷新显示
        Log.e(TAG, "moveXE -> " + event.getAction() +"");
        switch (event.getAction()) {
            // case MotionEvent.ACTION_DOWN: // 按下 尽量减少onDraw()的调用
            case MotionEvent.ACTION_MOVE: // 移动
                // case MotionEvent.ACTION_UP: // 抬起 尽量减少onDraw()的调用
                float moveX = event.getX();//event.getX()相对于当前控件的位置   event.getRawX()获取幕的x位置
                // Log.e(TAG, "moveX -> " + moveX +"");
                // 计算分数
                int currentGrade = (int) (moveX/mStarFocusBitmap.getWidth()+1);

                // 范围问题
                if(currentGrade<0){
                    currentGrade = 0;
                }
                if(currentGrade>mStarNumber){
                    currentGrade = mStarNumber;
                }
                // 分数相同的情况下不要绘制了 , 尽量减少onDraw()的调用
                if(currentGrade == mCurrentProgress){
                    return true;
                }

                // 再去刷新显示
                mCurrentProgress = currentGrade;
                invalidate();// onDraw()  尽量减少onDraw()的调用，目前是不断调用，怎么减少？
                break;
        }
        return true;// onTouch 后面看源码（2天,3个小时） false 不消费 第一次 DOWN false DOWN以后的事件是进不来的
    }

}
