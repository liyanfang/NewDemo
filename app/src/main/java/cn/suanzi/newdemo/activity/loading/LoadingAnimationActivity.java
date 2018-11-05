package cn.suanzi.newdemo.activity.loading;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import cn.suanzi.newdemo.R;

public class LoadingAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoadingAnimationActivity.class.getSimpleName();
    private  ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loading1);
        image = (ImageView) findViewById(R.id.iv_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) image.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        image.clearAnimation();
    }
}
