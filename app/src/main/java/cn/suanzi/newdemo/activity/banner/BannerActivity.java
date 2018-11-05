package cn.suanzi.newdemo.activity.banner;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;


import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.CharEvaluator;
import cn.suanzi.newdemo.view.MyTextView;

public class BannerActivity extends AppCompatActivity {
    private MyTextView mMyTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        mMyTv = (MyTextView) findViewById(R.id.tv_test2);
    }

    public void onBtnClick(View view) {
        Log.d("TAG", "onBtnClick: ces");
//        setAnimator();
        onKeyframe();
        doOfObject();
    }

    private void onKeyframe ()  {
        TextView textView = (TextView) findViewById(R.id.tv_view);
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2,frame4,frame5,frame6,frame7,frame8,frame9,frame10);
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(textView,frameHolder);
        animator.setDuration(3000);
        animator.start();
    }

    private void doOfObject () {

        PropertyValuesHolder charHolder = PropertyValuesHolder.ofObject("CharText",new CharEvaluator(),new Character('A'),new Character('Z'));
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mMyTv, charHolder);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private void setAnimator () {
        TextView textView = (TextView) findViewById(R.id.tv_view);
        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0xffffffff, 0xffff00ff, 0xffffff00, 0xffffffff);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(textView, rotationHolder, colorHolder);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();

    }
}
