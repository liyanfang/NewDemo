package cn.suanzi.newdemo.activity.loading;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;

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
                onShare();
//                startActivity(new Intent(this, LoadingAnimationActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, LoadingFrameAnimationActivity.class));
                break;
            case R.id.btn_0:
//                startActivity(new Intent(this, LoadingActivity.class));

                break;
            default:
        }
    }

    private void onShare () {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
// 查询所有可以分享的Activity
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo info : resInfo) {
                Intent targeted = new Intent(Intent.ACTION_SEND);
                targeted.setType("text/plain");
                ActivityInfo activityInfo = info.activityInfo;
                Log.v("logcat", "packageName=" + activityInfo.packageName + "Name=" + activityInfo.name);
                // 分享出去的内容
                targeted.putExtra(Intent.EXTRA_TEXT, "这是我的分享内容" + getPackageName());
                // 分享出去的标题
                targeted.putExtra(Intent.EXTRA_SUBJECT, "主题");
                targeted.setPackage(activityInfo.packageName);
                targeted.setClassName(activityInfo.packageName, info.activityInfo.name);
                PackageManager pm = getApplication().getPackageManager();
                // 微信有2个怎么区分-。- 朋友圈还有微信
                if (info.activityInfo.applicationInfo.loadLabel(pm).toString().equals("微信")) {
                    targetedShareIntents.add(targeted);
                }
            }
            // 选择分享时的标题
            Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "选择分享");
            if (chooserIntent == null) {
                return;
            }
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[] {}));
            try {
                startActivity(chooserIntent);
            } catch (android.content.ActivityNotFoundException ex) {

                Toast.makeText(this, "找不到该分享应用组件", Toast.LENGTH_SHORT).show();
            }}
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
