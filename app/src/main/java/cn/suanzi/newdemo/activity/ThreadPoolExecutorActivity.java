package cn.suanzi.newdemo.activity;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.ThreadPoolExecutorUtil;
import cn.suanzi.newdemo.View.YxBanner;
import cn.suanzi.newdemo.call.YxBannerCallBack;
import cn.suanzi.newdemo.pojo.BannerPojo;

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
