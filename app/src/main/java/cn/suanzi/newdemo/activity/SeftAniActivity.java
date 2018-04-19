package cn.suanzi.newdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.View.Rotate3dAnimation;

public class SeftAniActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final String TAG = SeftAniActivity.class.getSimpleName();
    private static final int FLING_MIN_DISTANCE = 100;
    private ViewGroup mContainer;
    private LinearLayout mLyAa;
    private LinearLayout mLyBb;
    private GestureDetector detector;

    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sefl);
        detector = new GestureDetector(this);
        mContainer = (ViewGroup) findViewById(R.id.ly_text);
        mLyAa = (LinearLayout) findViewById(R.id.rl_aa);
        mLyBb = (LinearLayout) findViewById(R.id.rl_bb);
    }

    private void applyRotation(float start, float end) {
        // 计算中心点
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setAnimationListener(new DisplayNextView());
        mContainer.startAnimation(rotation);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE) { //
            if (mPosition == 1) {
                mPosition = 0;
                applyRotation(0, 90);
            }
            return true;
        }
        if (e1.getX() - e2.getX() < -FLING_MIN_DISTANCE) {
            if (mPosition == 0) {
                mPosition = 1;
                applyRotation(180, 90);
            }
            return true;
        }
        return false;
    }

    private final class DisplayNextView implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mContainer.post(new SwapViews(mPosition));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private final class SwapViews implements Runnable {
        public SwapViews(int position) {
            mPosition = position;
        }

        @Override
        public void run() {
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            Rotate3dAnimation rotation;

            if (mPosition == 0) {
                //显示ImageView


                mLyBb.setVisibility(View.GONE);
                mLyAa.setVisibility(View.VISIBLE);
                mLyAa.requestFocus();

                rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);
            } else {
                //返回listview
                mLyAa.setVisibility(View.GONE);
                mLyBb.setVisibility(View.VISIBLE);
                mLyBb.requestFocus();
                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            //开始动画
            mContainer.startAnimation(rotation);
        }
    }
}
