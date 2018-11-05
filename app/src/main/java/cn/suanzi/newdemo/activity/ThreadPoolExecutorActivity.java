package cn.suanzi.newdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.ThreadPoolExecutorUtil;

/**
 * 线程池
 */
public class ThreadPoolExecutorActivity extends AppCompatActivity{
    private static final String TAG = ThreadPoolExecutorActivity.class.getSimpleName();

    ThreadPoolExecutorUtil threadPoolExecutorUtil;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_thread);
        threadPoolExecutorUtil = new ThreadPoolExecutorUtil();
    }

    public void onClick(View view) {
        for (int i = 0; i < 50; i++) {
            threadPoolExecutorUtil.addEventQueue("你好:::" + index++ ).start();
        }

    }

}
