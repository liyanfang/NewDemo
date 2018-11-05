package cn.suanzi.newdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.Util.DateUtil;
import cn.suanzi.newdemo.Util.FileUtil;
import cn.suanzi.newdemo.Util.StatusBarCompat;
import cn.suanzi.newdemo.Util.ZipUtil;
import cn.suanzi.newdemo.activity.AdImageViewActivity;
import cn.suanzi.newdemo.activity.animation.AnimTextActivity;
import cn.suanzi.newdemo.activity.animation.AnimationActivity;
import cn.suanzi.newdemo.activity.banner.Banner2Activity;
import cn.suanzi.newdemo.activity.banner.BannerActivity;
import cn.suanzi.newdemo.activity.banner.BannerViewActivity;
import cn.suanzi.newdemo.activity.BarrageActivity;
import cn.suanzi.newdemo.activity.CacheActivity;
import cn.suanzi.newdemo.activity.CircleIndicatorViewActivity;
import cn.suanzi.newdemo.activity.animation.ProgressActvity;
import cn.suanzi.newdemo.activity.list.ChatListActivity;
import cn.suanzi.newdemo.activity.list.CoordinatorLayoutActivity;
import cn.suanzi.newdemo.activity.DrawViewActivity;
import cn.suanzi.newdemo.activity.DrawingViewActivity;
import cn.suanzi.newdemo.activity.FullAnimationActivity;
import cn.suanzi.newdemo.activity.HighlightActivity;
import cn.suanzi.newdemo.activity.ImageBlurryActivity;
import cn.suanzi.newdemo.activity.loading.LoadingActivity;
import cn.suanzi.newdemo.activity.MainActivity;
import cn.suanzi.newdemo.activity.list.RecycleListActivity;
import cn.suanzi.newdemo.activity.slide.DrawerLayoutActivity;
import cn.suanzi.newdemo.activity.slide.QQCeHuaActivity;
import cn.suanzi.newdemo.activity.list.RecyclerScrollActivity;
import cn.suanzi.newdemo.activity.list.RecyclerViewActivity;
import cn.suanzi.newdemo.activity.list.ScrollConflictActivity;
import cn.suanzi.newdemo.activity.SeftAniActivity;
import cn.suanzi.newdemo.activity.viewpager.TableyoutActivity;
import cn.suanzi.newdemo.activity.ThreadPoolExecutorActivity;
import cn.suanzi.newdemo.activity.Transition3dActivity;
import cn.suanzi.newdemo.activity.VideoActivity;
import cn.suanzi.newdemo.activity.ViewFlipperActivity;
import cn.suanzi.newdemo.activity.WebViewAcitvity;
import cn.suanzi.newdemo.activity.viewpager.ViewPagerActivity;
import cn.suanzi.newdemo.adapter.HomeAdapter;
import cn.suanzi.newdemo.pojo.Home;

import static cn.suanzi.newdemo.Util.SystemUtil.showSystemParameter;

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

        showSystemParameter();

        try {
            String aa = "11";
            File test = FileUtil.getCacheFile(this, "/log");
            File outputDirectory = FileUtil.getCacheFile(this, "/output");
            String fileName = "/log-" + DateUtil.getNowTimeYMD(DateUtil.FORMAT) + ".log";
            String fileName_zip = "/log-" + DateUtil.getNowTimeYMD(DateUtil.FORMAT) + ".zip";
            writeFile(aa, test.getPath(), fileName);
            Log.d(TAG, "onCreate1111 : " + test.getPath()+fileName);
            ZipUtil.zip(test.getPath()+fileName, test.getPath() + fileName_zip);
            Log.d(TAG, "onCreate1111 : " + test.getPath()+fileName);


            ZipUtil.unzip(test.getPath() + fileName_zip, outputDirectory.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写日志
     * @param sb 日志内容
     * @return
     */
    public String writeFile(String sb, String canPath, String fileName) {
        Log.d(TAG, "writeFile: 写日志" );
        if (fileName == null) {
            fileName = "log-" + DateUtil.getNowTimeYMD(DateUtil.FORMAT) + ".log";
        }
        if (TextUtils.isEmpty(canPath)) {
            return null;
        }
        File dir = new File(canPath);
        if (!dir.exists())
            dir.mkdirs();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(canPath + fileName, true);
            fos.write(sb.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.d(TAG, "writeFile: " + e.getMessage(), e);
        }
        return fileName;
    }

    private List<Home> getListData () {
        List<Home> homes = new ArrayList<>();
        Home mainHome = new Home<>("侧滑动画", 1, MainActivity.class);
        homes.add(mainHome);
        Home home2 = new Home<>("跑马灯", 2, AnimTextActivity.class);
        homes.add(home2);
        Home home3 = new Home<>("视频弹幕", 3, BarrageActivity.class);
        homes.add(home3);
        Home home4 = new Home<>("反正动画", 4, Transition3dActivity.class);
        homes.add(home4);
        Home home5 = new Home<>("SEFL", 5, SeftAniActivity.class);
        homes.add(home5);
        Home home6 = new Home<>("CACHE", 7, CacheActivity.class);
        homes.add(home6);
        Home home8 = new Home<>("开关动画", 8, AnimationActivity.class);
        homes.add(home8);
        Home home9 = new Home<>("Tableyout", 9, TableyoutActivity.class);
        homes.add(home9);
        Home home10 = new Home<>("侧滑+动画", 10, FullAnimationActivity.class);
        homes.add(home10);
        Home home11 = new Home<>("画图", 11, DrawViewActivity.class);
        homes.add(home11);
        Home home12 = new Home<>("模仿支付宝滑动demo", 12, CoordinatorLayoutActivity.class);
        homes.add(home12);
        Home home14 = new Home<>("RecyclerView示例", 14, RecyclerViewActivity.class);
        homes.add(home14);
        Home recyclerScrollActivity = new Home<>("scrollView/recycleview", 15, RecyclerScrollActivity.class);
        homes.add(0, recyclerScrollActivity);
        Home webViewAcitvity = new Home<>("WebView html 下载", 16, WebViewAcitvity.class);
        homes.add(0, webViewAcitvity);
        Home adImageViewActivity = new Home<>("模仿知乎广告图", 17, AdImageViewActivity.class);
        Home imageBlurryActivity = new Home<>("图片模糊处理", 18, ImageBlurryActivity.class);
        Home BannerActivity = new Home<>("Banner滚屏", 18, BannerActivity.class);
        homes.add(0, adImageViewActivity);
        homes.add(0, imageBlurryActivity);
        homes.add(0, BannerActivity);
        Home Banner2Activity = new Home<>("Banner滚屏测试", 18, Banner2Activity.class);
        homes.add(0, Banner2Activity);
        Home ViewFlipperActivity = new Home<>("垂直循环滚动", 18, ViewFlipperActivity.class);
        homes.add(0, ViewFlipperActivity);
        Home DrawingViewActivity = new Home<>("画的圆环", 18, DrawingViewActivity.class);
        homes.add(0, DrawingViewActivity);
        Home CircleIndicatorViewActivity = new Home<>("画图", 18, CircleIndicatorViewActivity.class);
        homes.add(0, CircleIndicatorViewActivity);
        Home LoadingActivity = new Home<>("loading", 18, LoadingActivity.class);
        homes.add(0, LoadingActivity);
        Home BannerViewActivity = new Home<>("Banner 轮播图", 18, BannerViewActivity.class);
        homes.add(0, BannerViewActivity);
        Home ThreadPoolExecutorActivity = new Home<>("线程池", 18, ThreadPoolExecutorActivity.class);
        homes.add(0, ThreadPoolExecutorActivity);
        Home ScrollConflictActivity = new Home<>("纵向滑动冲突", 18, ScrollConflictActivity.class);
        homes.add(0, ScrollConflictActivity);
        Home QQCeHuaActivity = new Home<>("QQ侧滑", 18, QQCeHuaActivity.class);
        homes.add(0, QQCeHuaActivity);
        Home VideoActivity = new Home<>("视频", 18, VideoActivity.class);
        homes.add(0, VideoActivity);
        Home DrawerLayoutActivity = new Home<>("侧滑界面", 18, DrawerLayoutActivity.class);
        homes.add(0, DrawerLayoutActivity);
        Home ViewPagerActivity = new Home<>("ViewPagerActivity", 18, ViewPagerActivity.class);
        homes.add(0, ViewPagerActivity);
        Home HighlightActivity = new Home<>("引导图", 18, HighlightActivity.class);
        homes.add(0, HighlightActivity);
        Home recycleListActivity = new Home<>("recycle 上拉加载", 18, RecycleListActivity.class);
        homes.add(0, recycleListActivity);
        Home progressActvity = new Home("进度条", 18, ProgressActvity.class);
        homes.add(0, progressActvity);
        Home ChatListActivity = new Home("聊天界面", 18, cn.suanzi.newdemo.activity.list.ChatListActivity.class);
        homes.add(0, ChatListActivity);
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
