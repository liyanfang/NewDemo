package cn.suanzi.newdemo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.suanzi.newdemo.Util.DataUtils;
import cn.suanzi.newdemo.Util.StatusBarCompat;
import cn.suanzi.newdemo.activity.AdImageViewActivity;
import cn.suanzi.newdemo.activity.AnimTextActivity;
import cn.suanzi.newdemo.activity.AnimationActivity;
import cn.suanzi.newdemo.activity.Banner2Activity;
import cn.suanzi.newdemo.activity.BannerActivity;
import cn.suanzi.newdemo.activity.BannerViewActivity;
import cn.suanzi.newdemo.activity.BarrageActivity;
import cn.suanzi.newdemo.activity.CacheActivity;
import cn.suanzi.newdemo.activity.CircleIndicatorViewActivity;
import cn.suanzi.newdemo.activity.CoordinatorLayoutActivity;
import cn.suanzi.newdemo.activity.DrawViewActivity;
import cn.suanzi.newdemo.activity.DrawingViewActivity;
import cn.suanzi.newdemo.activity.FullAnimationActivity;
import cn.suanzi.newdemo.activity.ImageBlurryActivity;
import cn.suanzi.newdemo.activity.LoadingActivity;
import cn.suanzi.newdemo.activity.MainActivity;
import cn.suanzi.newdemo.activity.QQCeHuaActivity;
import cn.suanzi.newdemo.activity.RecyclerScrollActivity;
import cn.suanzi.newdemo.activity.RecyclerViewActivity;
import cn.suanzi.newdemo.activity.ScrollConflictActivity;
import cn.suanzi.newdemo.activity.SeftAniActivity;
import cn.suanzi.newdemo.activity.TableyoutActivity;
import cn.suanzi.newdemo.activity.ThreadPoolExecutorActivity;
import cn.suanzi.newdemo.activity.Transition3dActivity;
import cn.suanzi.newdemo.activity.VideoActivity;
import cn.suanzi.newdemo.activity.ViewFlipperActivity;
import cn.suanzi.newdemo.activity.WebViewAcitvity;
import cn.suanzi.newdemo.adapter.HomeAdapter;
import cn.suanzi.newdemo.pojo.Home;
import cn.suanzi.newdemo.pojo.video.VideoItem;

public class StartActivity extends FragmentActivity{
    private static final String TAG = StartActivity.class.getSimpleName();
    Toast toast = null;
    private View mView;
    private TextView mToastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        RecyclerView mRecyclerView = findViewById(R.id.lv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        HomeAdapter mHomeAdapter = new HomeAdapter(this, getListData());
        mRecyclerView.setAdapter(mHomeAdapter);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#7349ef"), 112);
        Log.d(TAG, "----------------- 测试 ----------------");
        float sp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics());

//        Math.atan();

//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
//        engine.eval("");

    }

    private List<Home> getListData () {
        List<Home> homes = new ArrayList<>();
        Home<MainActivity> mainHome = new Home<>("侧滑动画", 1, MainActivity.class);
        homes.add(mainHome);
        Home<AnimTextActivity> home2 = new Home<>("跑马灯", 2, AnimTextActivity.class);
        homes.add(home2);
        Home<BarrageActivity> home3 = new Home<>("视频弹幕", 3, BarrageActivity.class);
        homes.add(home3);
        Home<Transition3dActivity> home4 = new Home<>("反正动画", 4, Transition3dActivity.class);
        homes.add(home4);
        Home<SeftAniActivity> home5 = new Home<>("SEFL", 5, SeftAniActivity.class);
        homes.add(home5);
        Home<CacheActivity> home6 = new Home<>("CACHE", 7, CacheActivity.class);
        homes.add(home6);
        Home<AnimationActivity> home8 = new Home<>("开关动画", 8, AnimationActivity.class);
        homes.add(home8);
        Home<TableyoutActivity> home9 = new Home<>("Tableyout", 9, TableyoutActivity.class);
        homes.add(home9);
        Home<FullAnimationActivity> home10 = new Home<>("侧滑+动画", 10, FullAnimationActivity.class);
        homes.add(home10);
        Home<DrawViewActivity> home11 = new Home<>("画图", 11, DrawViewActivity.class);
        homes.add(home11);
        Home<CoordinatorLayoutActivity> home12 = new Home<>("模仿支付宝滑动demo", 12, CoordinatorLayoutActivity.class);
        homes.add(home12);
        Home<RecyclerViewActivity> home14 = new Home<>("RecyclerView示例", 14, RecyclerViewActivity.class);
        homes.add(home14);
        Home<RecyclerScrollActivity> recyclerScrollActivity = new Home<>("scrollView/recycleview", 15, RecyclerScrollActivity.class);
        homes.add(0, recyclerScrollActivity);
        Home<WebViewAcitvity> webViewAcitvity = new Home<>("WebView html 下载", 16, WebViewAcitvity.class);
        homes.add(0, webViewAcitvity);
        Home<AdImageViewActivity> adImageViewActivity = new Home<>("模仿知乎广告图", 17, AdImageViewActivity.class);
        Home<ImageBlurryActivity> imageBlurryActivity = new Home<>("图片模糊处理", 18, ImageBlurryActivity.class);
        Home<BannerActivity> BannerActivity = new Home<>("Banner滚屏", 18, BannerActivity.class);
        homes.add(0, adImageViewActivity);
        homes.add(0, imageBlurryActivity);
        homes.add(0, BannerActivity);
        Home<Banner2Activity> Banner2Activity = new Home<>("Banner滚屏测试", 18, Banner2Activity.class);
        homes.add(0, Banner2Activity);
        Home<ViewFlipperActivity> ViewFlipperActivity = new Home<>("垂直循环滚动", 18, ViewFlipperActivity.class);
        homes.add(0, ViewFlipperActivity);
        Home<DrawingViewActivity> DrawingViewActivity = new Home<>("画的圆环", 18, DrawingViewActivity.class);
        homes.add(0, DrawingViewActivity);
        Home<CircleIndicatorViewActivity> CircleIndicatorViewActivity = new Home<>("画图", 18, CircleIndicatorViewActivity.class);
        homes.add(0, CircleIndicatorViewActivity);
        Home<LoadingActivity> LoadingActivity = new Home<>("loading", 18, LoadingActivity.class);
        homes.add(0, LoadingActivity);
        Home<BannerViewActivity> BannerViewActivity = new Home<>("Banner 轮播图", 18, BannerViewActivity.class);
        homes.add(0, BannerViewActivity);
        Home<ThreadPoolExecutorActivity> ThreadPoolExecutorActivity = new Home<>("线程池", 18, ThreadPoolExecutorActivity.class);
        homes.add(0, ThreadPoolExecutorActivity);
        Home<ScrollConflictActivity> ScrollConflictActivity = new Home<>("纵向滑动冲突", 18, ScrollConflictActivity.class);
        homes.add(0, ScrollConflictActivity);
        Home<QQCeHuaActivity> QQCeHuaActivity = new Home<>("QQ侧滑", 18, QQCeHuaActivity.class);
        homes.add(0, QQCeHuaActivity);
        Home<VideoActivity> VideoActivity = new Home<>("视频", 18, VideoActivity.class);
        homes.add(0, VideoActivity);
        return homes;
    }

    private void showToast(String content) {
        if (toast == null) {
            toast = new Toast(this);
        }
        if (mView == null) {
            mView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_toast, null);
            mToastView = mView.findViewById(R.id.tv_toast);
        }
        GradientDrawable p = (GradientDrawable) mView.getBackground();
        p.setColor(Color.parseColor("#7f000000"));
        mToastView.setText(content);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(mView);
        toast.setGravity(Gravity.TOP, 0, dip2px(getApplicationContext(), 48));
        toast.show();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
