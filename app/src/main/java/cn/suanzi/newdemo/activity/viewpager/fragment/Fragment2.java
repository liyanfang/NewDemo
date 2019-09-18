package cn.suanzi.newdemo.activity.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.activity.viewpager.MyFragmentPagerAdapter;

public class Fragment2 extends Fragment {

    private SlidingTabLayout tabProfit;
    private ViewPager viewpager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private View view;

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test_viewpager_act, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData () {

        tabProfit = view.findViewById(R.id.tab_profit);
        viewpager = view.findViewById(R.id.viewpager);

        fragmentList.add(Fragment3.newInstance());
        fragmentList.add(Fragment3.newInstance());
        fragmentList.add(Fragment3.newInstance());
        String[] titles = {"关注", "好友", "粉丝"};
        MyFragmentPagerAdapter teamViewPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        viewpager.setAdapter(teamViewPagerAdapter);
        tabProfit.setViewPager(viewpager, titles);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (callback != null) {
                    callback.onResult(titles[position]);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public static Fragment2 newInstance () {
        Fragment2 fragment1 = new Fragment2();
        Bundle bundle = new Bundle();
        fragment1.setArguments(bundle);
        return fragment1;
    }

    public interface Callback {
        void onResult(String title);
    }
}
