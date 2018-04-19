package cn.suanzi.newdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.suanzi.newdemo.R;

public class ObjectAnimatorActivity extends AppCompatActivity{
    private static final String TAG = ObjectAnimatorActivity.class.getSimpleName();
    private static final int FLING_MIN_DISTANCE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectanimator);

    }

//    private void applyRotation(float start, float end) {
//        // 计算中心点
//        final float centerX = mContainer.getWidth() / 2.0f;
//        final float centerY = mContainer.getHeight() / 2.0f;
//        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
//        rotation.setDuration(500);
//        rotation.setFillAfter(true);
//        rotation.setAnimationListener(new DisplayNextView());
//        mContainer.startAnimation(rotation);
//    }

}
