package cn.suanzi.newdemo.activity.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.activity.viewpager.fragment.Fragment1;
import cn.suanzi.newdemo.activity.viewpager.fragment.Fragment2;

/**
 * @author liyanfang
 */
public class TestViewpagerActivity extends FragmentActivity {

    private SlidingTabLayout tabProfit;
    private ViewPager viewpager;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_viewpager_act);
        tabProfit = findViewById(R.id.tab_profit);
        viewpager = findViewById(R.id.viewpager);
        initData();
    }

    private void initData () {
        fragmentList.add(Fragment1.newInstance());
        Fragment2 fragment2 = Fragment2.newInstance();
        fragmentList.add(fragment2);

        String[] titles = {"爱心", "好友"};

        MyFragmentPagerAdapter teamViewPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewpager.setAdapter(teamViewPagerAdapter);
        tabProfit.setViewPager(viewpager, titles);

        fragment2.setCallback(title -> {
            titles[1] = title;
            tabProfit.setViewPager(viewpager, titles);
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
