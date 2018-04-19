package cn.suanzi.newdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.suanzi.newdemo.R;

public class TranslateAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = TranslateAnimationActivity.class.getSimpleName();
    ScaleAnimation mScaleAnimation;
    TextView ttgTvListTitle;
    LinearLayout lyContent;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        ttgTvListTitle = (TextView) findViewById(R.id.ttg_tv_list_title);
        lyContent = (LinearLayout) findViewById(R.id.ly_content);
        Button btnClick = (Button) findViewById(R.id.btn_id6);
        Button btnStop = (Button) findViewById(R.id.btn_id7);

        if (btnClick != null)
             btnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mScaleAnimation= new ScaleAnimation(0, 1.0f, 0, 1.0f, 100.0f, 0);
                    mScaleAnimation = new ScaleAnimation(0.8f, 1f, 1, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    mScaleAnimation.setDuration(1000);
                    mScaleAnimation.setRepeatCount(0); // 只执行一次动画
                    lyContent.startAnimation(mScaleAnimation);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(1000);
                    scaleAnimation.setRepeatCount(0); // 只执行一次动画
                    ttgTvListTitle.startAnimation(scaleAnimation);
                    ttgTvListTitle.setVisibility(View.VISIBLE);
                    lyContent.setVisibility(View.VISIBLE);
                    mScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
        if (btnStop != null)
            btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyContent.setVisibility(View.GONE);
                if (mScaleAnimation != null) {
//                    mIvBallC.clearAnimation();
                    mScaleAnimation.cancel();
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
