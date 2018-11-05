package cn.suanzi.newdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;

/**
 * 垂直循环滚动的效果
 * 在xml布局中的方法介绍:

 android:autoStart：   设置自动加载下一个View

 android:flipInterval：设置View之间切换的时间间隔

 android:inAnimation： 设置切换View的进入动画

 android:outAnimation：设置切换View的退出动画

 当然同样的在代码中也可以设置：

 isFlipping：     判断View切换是否正在进行

 setFilpInterval：设置View之间切换的时间间隔

 startFlipping：  开始View的切换，而且会循环进行

 stopFlipping：   停止View的切换

 setOutAnimation：设置切换View的退出动画

 setInAnimation： 设置切换View的进入动画

 showNext：       显示ViewFlipper里的下一个View

 showPrevious：   显示ViewFlipper里的上一个View
 */
public class ViewFlipperActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ViewFlipperActivity.class.getSimpleName();

    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_viewflipper);
        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.filpper);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_custom, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_test);
            textView.setText("第 " + (i + 1) + " 条数据");
            viewFlipper.addView(view);
        }
    }

    private List<String> getData () {
        List<String> mockDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mockDatas.add(i + "");
        }
        return mockDatas;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                viewFlipper.stopFlipping();
                break;
            case R.id.btn_stop:
                viewFlipper.startFlipping();
                break;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
