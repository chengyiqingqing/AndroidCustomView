package com.sww.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.sww.customview.multicolorTextView.MultiColorTextView;
import com.sww.customview.paintuse.QQStepView;
import com.sww.customview.textview.MarqueeView;

public class MainActivity extends AppCompatActivity {

    private QQStepView qqStepView;
    private MultiColorTextView multiColorTextView;
    private MarqueeView marqueeView;
    private String[] stringList = new String[]{
            "哈哈哈哈哈哈哈哈哈哈1",
            "嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿2",
            "哈哈哈哈哈哈哈哈哈哈3",
            "嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿4",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        qqStepView = findViewById(R.id.qq_step_view);
        multiColorTextView = findViewById(R.id.multi_color_text_view);
        marqueeView = findViewById(R.id.marquee_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startQQStepView();
        startMultiColorTextView();
    }

    public void startQQStepView(){
        qqStepView.setStepMax(4000);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,3000);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStep = (float) animation.getAnimatedValue();
                qqStepView.setCurrentStep((int) currentStep);
            }
        });
        valueAnimator.start();
    }

    public void startMultiColorTextView(){
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,0.6f);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStep = (float) animation.getAnimatedValue();
                multiColorTextView.setCurrentProgressRatio(currentStep);
            }
        });
        valueAnimator.start();
    }

}
