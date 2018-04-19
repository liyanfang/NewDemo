package cn.suanzi.newdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.activity.BannerActivity;
import cn.suanzi.newdemo.adapter.GlideImageLoader;
import cn.suanzi.newdemo.pojo.BannerPojo;

/**
 * Created by liyanfang on 2016/7/16.
 */
public class Fragment1 extends BaseFragment implements OnBannerListener {
    private static final String TAG = Fragment1.class.getSimpleName();
    private static final String ARG_POSITION = "position";
    private int mPosition;
    TextView tvToast;
    private ListView listview;
    /** 测试*/
    private List<String> mData = new ArrayList<>();
    /** 列表头部的view*/
    private View headView;

    Banner banner;

    /** 下拉*/
    private SwipeRefreshLayout mSfSwipeRefreshLayout;

    public static Fragment1 newInstance(int position) {
        Fragment1 f = new Fragment1();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
        // 初始化列表数据
        initListData();
    }

    /**
     * 初始化列表数据
     */
    private void initListData () {
        for (int i = 0; i < 20; i++) {
            mData.add("测试数据 第 " + i + " 条");
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment1,null);
            headView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_banner2, null);
            tvToast = (TextView) mView.findViewById(R.id.tv_toast);
            listview = (ListView) mView.findViewById(R.id.listview);
            mSfSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.sf_swiperefreshlayout);
            tvToast.setText("当前 第 " + mPosition + " 个fragment");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, mData);
            listview.setAdapter(adapter);
            listview.addHeaderView(headView);
            setBanner();
            setSwipeRefreshLayout();
        }
        return mView;
    }

    private void setSwipeRefreshLayout () {
        mSfSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        stopSwipeRefreshLayout();
                    }
                }, 3000);
            }
        });
        setSwipeColcor(mSfSwipeRefreshLayout);
    }

    public void stopSwipeRefreshLayout() {
        if (mSfSwipeRefreshLayout != null) {
            mSfSwipeRefreshLayout.setRefreshing(false);

        }
    }

    public void setSwipeColcor (SwipeRefreshLayout swipeToLoadLayout) {
        swipeToLoadLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void setBanner () {
        List<BannerPojo> images = new ArrayList<>();
        BannerPojo bannerPojo1 = new BannerPojo("https://cdnbdimg.tatagou.com.cn/1508902016497c.jpg", "1");
        BannerPojo bannerPojo2 = new BannerPojo("https://cdnbdimg.tatagou.com.cn/1508902103f6ab.jpg", "2");
        BannerPojo bannerPojo3 = new BannerPojo("https://cdnbdimg.tatagou.com.cn/15089021634373.jpg", "3");
        images.add(bannerPojo1);
        images.add(bannerPojo2);
        images.add(bannerPojo3);
        banner = (Banner) headView.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        // 设置轮播时间
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(this);
    }

    @Override
    public void transferMethod(int position) {
        super.transferMethod(position);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            banner.startAutoPlay();
            Log.d(TAG, "testbanner onResume: startAutoPlay");
        }
        if (mFirstFlag) {
            Log.d(TAG, "onCreate onResume: Fragment1");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (banner != null) {
            Log.d(TAG, "testbanner onResume: stopAutoPlay");
            banner.stopAutoPlay();
        }
    }

    @Override
    public void OnBannerClick(int position) {
        getActivity().startActivity(new Intent(getActivity(), BannerActivity.class));
    }
}
