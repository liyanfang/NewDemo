package cn.suanzi.newdemo.activity.list;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.Callback;
import cn.suanzi.newdemo.view.MySwipeRefreshLayout;
import cn.suanzi.newdemo.view.TtgPagerSlidingTab;
import cn.suanzi.newdemo.adapter.ChannelCateAdapter;
import cn.suanzi.newdemo.pojo.Cates;

/**
 * Created by liyanfang on 2017/11/27.
 */
public class RecyclerScrollActivity extends FragmentActivity {

    private List<Cates> mDatas;
    /** 选项卡*/
    private TtgPagerSlidingTab mTabView;
    /** 类目适配器*/
    private ChannelCateAdapter mChannelCateAdapter;
    /** viewpager*/
    private ViewPager mViewPager;
    /** 下拉*/
    private MySwipeRefreshLayout mSfSwipeRefreshLayout;
    /** 最外层的滑动布局*/
//    private ScrollInterceptScrollView mSvView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recycle_scroll);
        mTabView = (TtgPagerSlidingTab) findViewById(R.id.ttg_pager_sliding_tab);
        mViewPager = (ViewPager) findViewById(R.id.ttg_viewPage_fragment);
        mSfSwipeRefreshLayout = (MySwipeRefreshLayout) findViewById(R.id.sf_swiperefreshlayout);
//        mSvView = (ScrollInterceptScrollView) findViewById(R.id.ttg_sv_view);
        mTabView.setOnPageSelectedLister(callback);
        initDatas();
        init();
        setTabView();
        initSfSwipeRefreshLayout();
    }

    private Callback callback = new Callback(){
        @Override
        public void setPageSelected(int position) {
            super.setPageSelected(position);
        }

        @Override
        public void setOnClickListener(int position) {
            super.setOnClickListener(position);
            mViewPager.setCurrentItem(position, false);
        }

        @Override
        public void onPullDown(boolean isClose) {
            super.onPullDown(isClose);
            Log.d("TTG", "onPullDown: 1111111111111111");
            stopSwipeRefreshLayout();
        }

        @Override
        public void onLoadMore(boolean isClose) {
            super.onLoadMore(isClose);
            isLoadFlag = true;
        }
    };

    public void stopSwipeRefreshLayout() {
        if (mSfSwipeRefreshLayout != null) {
            mSfSwipeRefreshLayout.setRefreshing(false);

        }
    }

    private void initSfSwipeRefreshLayout () {
        mSfSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mChannelCateAdapter != null && mChannelCateAdapter.mFragment != null) {
                    mChannelCateAdapter.mFragment.onPullDown();
                }
            }
        });
        setSwipeColcor(mSfSwipeRefreshLayout);
    }

    public void setSwipeColcor (SwipeRefreshLayout swipeToLoadLayout) {
        swipeToLoadLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private boolean isLoadFlag = true;
//



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getX() > 0 && isLoadFlag)  {
            isLoadFlag = false;
            mChannelCateAdapter.mFragment.onAutoLoadMore();
        }
        return super.onTouchEvent(event);
    }

    public void init () {
//        mSvView.setScanScrollChangedListener(new ScrollInterceptScrollView.ISmartScrollChangedListener() {
//            @Override
//            public void onScrolledToBottom() {
//                if (mChannelCateAdapter != null && mChannelCateAdapter.mFragment != null && isLoadFlag) {
//
//                }
//            }
//
//            @Override
//            public void onScrolledToTop() {
//
//            }
//        });
    }

    private void setTabView () {
        mViewPager.setOffscreenPageLimit(2);
        if (mChannelCateAdapter == null) {
            mChannelCateAdapter = new ChannelCateAdapter(this.getSupportFragmentManager(), mDatas, 0, callback);
            mViewPager.setAdapter(mChannelCateAdapter);
        } else {
            mChannelCateAdapter.setItem(mDatas);
        }
        mTabView.setViewPager(mViewPager);
    }

    private void initDatas(){
        Cates cates = new Cates("男士", "23");
        Cates cates2 = new Cates("居家日用", "11");
        Cates cates3 = new Cates("数码家电", "16");
        Cates cates4 = new Cates("美食", "21");
        Cates cates5 = new Cates("女士123", "6");
        Cates cates6 = new Cates("橘子", "46");
        Cates cates7 = new Cates("精选", "23");
        Cates cates8 = new Cates("居家日用", "11");
        Cates cates9 = new Cates("数码家电", "16");
        Cates cates10 = new Cates("精选", "11");
        mDatas = new ArrayList<>();
        mDatas.add(cates);
        mDatas.add(cates2);
        mDatas.add(cates3);
        mDatas.add(cates4);
        mDatas.add(cates5);
        mDatas.add(cates6);
        mDatas.add(cates7);
        mDatas.add(cates8);
        mDatas.add(cates9);
        mDatas.add(cates10);
    }

    private List<String> getList(String index){
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("第一条测试数据：", "第二条测试数据：", "测试数据……", "测试数据", "测试数据……", "测试数据……33333", "你好", "测试数据……", "测试数据……222", "测试数据%%%%%%%%%%", "测试数据……", "测试数据……", "你好", "测试数据……", "测试数据……", "测试数据%%%%%%%%%%", "测试数据……", "测试数据……"));
        List<String> newlist = new ArrayList<>();
        for (String str : strings) {
            newlist.add(str.concat(index));
        }
        return newlist;
    }

}
