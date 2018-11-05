package cn.suanzi.newdemo.activity.animation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.suanzi.newdemo.R;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = AnimationActivity.class.getSimpleName();
    TranslateAnimation mTranslateAnimation;
    ImageView mIvSwich;
    LinearLayout mLySwitch;
    private boolean flag = false;
    Button btnClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_animation);
        btnClick = (Button) findViewById(R.id.btn_id6);
        mIvSwich = (ImageView) findViewById(R.id.iv_swich);
        mLySwitch = (LinearLayout) findViewById(R.id.ly_switch);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int distance = dip2px(getApplicationContext(),19);
                if (flag) {
                    flag = false;
                    mLySwitch.setBackgroundResource(R.drawable.shape_switch_pink);
                    mTranslateAnimation = new TranslateAnimation(distance,0,0,0);
                } else {
                    flag = true;
                    mLySwitch.setBackgroundResource(R.drawable.shape_switch_gray);
                    mTranslateAnimation = new TranslateAnimation(0,distance,0,0);

                }
                mTranslateAnimation.setDuration(400);
                mTranslateAnimation.setFillAfter(true);
                mIvSwich.startAnimation(mTranslateAnimation);
                mTranslateAnimation.startNow();
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
