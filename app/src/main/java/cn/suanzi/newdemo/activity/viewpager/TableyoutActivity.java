package cn.suanzi.newdemo.activity.viewpager;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.CategoryTabStrip;
import cn.suanzi.newdemo.view.CustomViewPager;
import cn.suanzi.newdemo.view.ScaleTransformer;
import cn.suanzi.newdemo.adapter.ViewPageAdapter;

public class TableyoutActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = TableyoutActivity.class.getSimpleName();
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private List<String> mTitleList = new ArrayList<String>();
    private TabLayout mTabLayout;
    private CategoryTabStrip mTabStrip;
    private static  CustomViewPager mViewPage;
    /** 当前*/
    private int mCurrent = 0;
    /**
     * 切换当前的Fragment
     * @param currentPosition
     */
    public static void setCurrentItemFragment(int currentPosition) {
        mViewPage.setCurrentItem(currentPosition,false);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablelayout);
        mTabLayout = (TabLayout) findViewById(R.id.tabLyout);
        mTabStrip = (CategoryTabStrip) findViewById(R.id.category_strip);
        mViewPage = (CustomViewPager) findViewById(R.id.viewPage);
        initData();
        mViewPage.setAdapter(new ViewPageAdapter(getSupportFragmentManager(),mTitleList));
//        mViewPage.setOffscreenPageLimit(mTitleList.size()); // 限定预加载的个数
//        mTabLayout.setupWithViewPager(mViewPage);
        mTabStrip.setViewPager(mViewPage);
//        mViewPage.setPageTransformer(true, new AlphaTransformer());
        mViewPage.setPageTransformer(true, new ScaleTransformer());


        mTabStrip.setOnTabSelectedListener(new CategoryTabStrip.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.d(TAG, "onTabSelected: " + position);
            }
        });
        mViewPage.setPageMargin(100);
//        mViewPage.setPageMarginDrawable(getResources().getDrawable(R.mipmap.feedback));
       mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                Log.d(TAG, "onTabSelected  111: " + tab.getPosition());
                mCurrent = tab.getPosition();
//                BaseFragment baseFragment = (BaseFragment) mFragmentList.get(mCurrent);
//                baseFragment.transferMethod(mCurrent);
                mViewPage.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void initData () {
        for (int i = 0; i < 10; i++) {
            mTitleList.add("她他购" + i);
        }
    }

    @Override
    public void onClick(View v) {

    }

}
