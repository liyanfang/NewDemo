package cn.suanzi.newdemo.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.View.FrameAnimation;

public class LoadingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoadingActivity.class.getSimpleName();
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loading);
        button = findViewById(R.id.btn_0);
        initView();
    }

    private  void initView () {
        TextView tvText = findViewById(R.id.tv_text);
        Typeface ttf = Typeface.createFromAsset(getAssets(), "font/Comic Neue.ttf");
        tvText.setTypeface(ttf);

        final LottieAnimationView lottieLoading = findViewById(R.id.lottie_loading);
        LottieComposition.Factory.fromAssetFileName(this, "lottiefiles.com - Emoji Tongue.json", lottieLoading::setComposition);
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                Log.d(TAG, "handleMessage 0:  " + Thread.currentThread().getName());
                button.setText("button");
            }
            return false;
        }
    });

    private void setHandlerMessage () {
        new Thread(() -> {
            Log.d(TAG, "handleMessage 2:  " + Thread.currentThread().getName());
            Message message = new Message();
            message.what = 0;
            handler.sendMessage(message);
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                startActivity(new Intent(this, LoadingAnimationActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, LoadingFrameAnimationActivity.class));
                break;
            case R.id.btn_0:
//                startActivity(new Intent(this, LoadingActivity.class));

                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "LoadingActivitytest onNewIntent: " );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "LoadingActivitytest onResume: " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "LoadingActivitytest onRestart: " );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "LoadingActivitytest onStart: " );
    }
}
