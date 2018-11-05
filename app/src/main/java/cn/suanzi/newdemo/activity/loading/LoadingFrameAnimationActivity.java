package cn.suanzi.newdemo.activity.loading;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.FrameAnimation;

public class LoadingFrameAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoadingFrameAnimationActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loading2);
        setFrameAnimation();
    }

    private void setFrameAnimation () {
        ImageView image = findViewById(R.id.iv_loading);
        FrameAnimation frameAnimation = new FrameAnimation(image, getRes(), 50, true);
        frameAnimation.setAnimationListener(new FrameAnimation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                Log.d(TAG, "start");
            }

            @Override
            public void onAnimationEnd() {
                Log.d(TAG, "end");
            }

            @Override
            public void onAnimationRepeat() {
                Log.d(TAG, "repeat");
            }
        });
    }
    private int[] getRes() {
        TypedArray typedArray = getResources().obtainTypedArray(R.array.loading);
        int len = typedArray.length();
        int[] resId = new int[len];
        for (int i = 0; i < len; i++) {
            resId[i] = typedArray.getResourceId(i, -1);
        }
        typedArray.recycle();
        return resId;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, LoadingActivity.class));
    }

}
