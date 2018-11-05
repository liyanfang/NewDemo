package cn.suanzi.newdemo.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.Rotate3dAnimation;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int FLING_MIN_DISTANCE = 100;
    private ViewFlipper flipper;
    private GestureDetector detector;
    private RotateAnimation animation;
    private ViewGroup mContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (ViewGroup) findViewById(R.id.ly_text);
        animation = new RotateAnimation(0,180,1,1F,1,1F);
        animation.setDuration(1000);
        // 注册一个GestureDetector
        detector = new GestureDetector((GestureDetector.OnGestureListener)this);
        flipper = (ViewFlipper) findViewById(R.id.viewFilpper);
        ImageView image1 = new ImageView(this);
        image1.setBackgroundResource(R.mipmap.a);
        // 增加第一个view
        flipper.addView(image1);
        ImageView image2 = new ImageView(this);
        image2.setBackgroundResource(R.mipmap.b);
        // 增加第二个view
        flipper.addView(image2);
        ImageView image3 = new ImageView(this);
        image3.setBackgroundResource(R.mipmap.c);
        // 增加第二个view
        flipper.addView(image3);


    }

    private void setAnimation (int type,float start, float end) {
        // 计算中心点
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        mContainer.startAnimation(rotation);
        if (type == 1) {
            this.flipper.setInAnimation(rotation);
        } else {
            this.flipper.setOutAnimation(rotation);
        }
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
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE) {
            Log.d(TAG, "onFling > : " + (e1.getX() - e2.getX()));
            //设置View进入和退出的动画效果
            this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
//            setAnimation(1,0,90);
            this.flipper.showNext();
            return true;
        }
        if (e1.getX() - e2.getX() < -FLING_MIN_DISTANCE) {
            setAnimation(1,0,90);
//            setAnimation(2,180,90);
            Log.d(TAG, "onFling <: " + (e1.getX() - e2.getX()));
//            setAnimation(2,180,90);
            this.flipper.showPrevious();
            return true;
        }
        return false;
    }
}
