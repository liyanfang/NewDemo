package cn.suanzi.newdemo.activity.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.Util;
import cn.suanzi.newdemo.adapter.ViewPagerAdapter;
import cn.suanzi.newdemo.fragment.Fragment1;
import cn.suanzi.newdemo.fragment.Fragment2;

/**
 * Created by liyanfang on 2018/7/13.
 */

public class ViewPagerActivity extends FragmentActivity {

    /** 第一个标题*/
    private TextView mTvTitle1;
    /** 第而个标题*/
    private TextView mTvTitle2;
    private TextView mTvLine;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_viewpager);
        initView();
    }

    int mPosition = 0;

    /**
     * 初始化布局
     */
    private void initView () {
        mTvTitle1 = findViewById(R.id.tv_title1);
        mTvTitle2 = findViewById(R.id.tv_title2);
        mTvLine = findViewById(R.id.tv_line);
        mViewPager = findViewById(R.id.viewPage);
        List<Fragment> list = new ArrayList<>();
        list.add(Fragment1.newInstance(0));
        list.add(Fragment1.newInstance(1));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int windowWidth = Util.getWindowWidth(ViewPagerActivity.this);

                if (mPosition == position) {
                    setLayout(mTvLine, positionOffsetPixels, 0);
                }
                mPosition = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /*
     * 设置控件所在的位置Y，并且不改变宽高，
     * Y为绝对位置，此时X可能归0
     */
    public static void setLayoutY(View view, int y)
    {
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(margin.leftMargin,y, margin.rightMargin, y+margin.height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
    /*
     * 设置控件所在的位置YY，并且不改变宽高，
     * XY为绝对位置
     */
    public static void setLayout(View view,int x,int y)
    {
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, x+margin.width, y+margin.height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }




}
