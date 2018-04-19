package cn.suanzi.newdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

import cn.suanzi.newdemo.fragment.Fragment1;

/**
 * Created by liyanfang on 2016/7/16.
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    private final static String TAG = ViewPageAdapter.class.getSimpleName();

    private List<String> mTitleList;

    public ViewPageAdapter(FragmentManager fm,List<String> titleList) {
        super(fm);
        this.mTitleList = titleList;

    }

    @Override
    public Fragment getItem(int position) {
        return Fragment1.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
