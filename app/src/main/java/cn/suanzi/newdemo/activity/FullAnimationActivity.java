package cn.suanzi.newdemo.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.SildingFinishLayout;

public class FullAnimationActivity extends Activity implements View.OnClickListener {
    private static final String TAG = FullAnimationActivity.class.getSimpleName();
    TranslateAnimation mTranslateAnimationL;
    TranslateAnimation mTranslateAnimationR;
    ScaleAnimation mScaleAnimation;
    ImageView mIvBallL;
    ImageView mIvBallR;
    ImageView mIvBallC;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText("手机型号: " + android.os.Build.MODEL + ",\nSDK版本:" + android.os.Build.VERSION.SDK + ",\n系统版本:" + android.os.Build.VERSION.RELEASE);
        mIvBallL = (ImageView) findViewById(R.id.iv_ball_l);
        mIvBallR = (ImageView) findViewById(R.id.iv_ball_r);
        mIvBallC = (ImageView) findViewById(R.id.iv_ball_c);
        Button btnClick = (Button) findViewById(R.id.btn_id6);
        Button btnStop = (Button) findViewById(R.id.btn_id7);

        SildingFinishLayout mSildingFinishLayout = (SildingFinishLayout) findViewById(R.id.ly_text);
        mSildingFinishLayout
                .setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {

                    @Override
                    public void onSildingFinish() {
                        FullAnimationActivity.this.finish();
                    }
                });
        FrameLayout fyContent = (FrameLayout) findViewById(R.id.ttg_fy_content);
        // touchView要设置到ListView上面
        mSildingFinishLayout.setTouchView(fyContent);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScaleAnimation = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                mScaleAnimation.setDuration(600);
                mScaleAnimation.setRepeatCount(ValueAnimator.INFINITE);
                mScaleAnimation.setRepeatMode(Animation.REVERSE);// 设置反方向执行
                mIvBallC.startAnimation(mScaleAnimation);
                mScaleAnimation.startNow();

                mTranslateAnimationL = new TranslateAnimation(dip2px(getApplicationContext(), 7), 0, 0, 0);
                mTranslateAnimationL.setFillAfter(false);
                mTranslateAnimationL.setDuration(600);
                mTranslateAnimationL.setRepeatCount(ValueAnimator.INFINITE);
                mTranslateAnimationL.setRepeatMode(Animation.REVERSE);// 设置反方向执行
                mIvBallL.startAnimation(mTranslateAnimationL);
                mTranslateAnimationL.startNow();

                mTranslateAnimationR = new TranslateAnimation(-dip2px(getApplicationContext(), 7), 0, 0, 0);
                mTranslateAnimationR.setDuration(600);
                mTranslateAnimationR.setRepeatCount(ValueAnimator.INFINITE);
                mTranslateAnimationR.setRepeatMode(Animation.REVERSE);// 设置反方向执行
                mIvBallR.startAnimation(mTranslateAnimationR);
                mTranslateAnimationR.startNow();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScaleAnimation != null) {
//                    mIvBallC.clearAnimation();
                    mScaleAnimation.cancel();
                }
                if (mTranslateAnimationR != null) {
//                    mIvBallR.clearAnimation();
                    mTranslateAnimationR.cancel();
                }
                if (mTranslateAnimationL != null) {
//                    mIvBallL.clearAnimation();
                    mTranslateAnimationL.cancel();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
