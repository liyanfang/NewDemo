package cn.suanzi.newdemo.adapter;

import android.database.DataSetObserver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import cn.suanzi.newdemo.Util.Callback;
import cn.suanzi.newdemo.fragment.RecycleScrollFragment;
import cn.suanzi.newdemo.pojo.Cates;

/**
 * 频道类目适配器
 * Created by liyanfang on 2016/7/16.
 */
public class ChannelCateAdapter extends FragmentStatePagerAdapter {
    /** 频道列表*/
    private List<Cates> mChannelList;
    /** 当前运行的fragment*/
    public RecycleScrollFragment mFragment;
    /** 专场ID*/
    private int mSpecialId;

    private Callback mCallback;

    /**
     * 刷新
     * @param subChannel 频道列表
     */
    public void setItem (List<Cates> subChannel) {
        this.mChannelList = subChannel;
        notifyDataSetChanged();
    }

    /**
     *  构造方法
     * @param fm fragment管理器
     * @param channelList 频道列表
     * @param specialId 专场ID
     */
    public ChannelCateAdapter(FragmentManager fm, List<Cates> channelList, int specialId, Callback callback) {
        super(fm);
        this.mChannelList = channelList;
        this.mSpecialId = specialId;
        this.mCallback = callback;
    }

    /**
     * 设置专场ID
     * @param specialId 专场ID
     */
    public void setSpecialId(int specialId) {
        mSpecialId = specialId;
    }

    @Override
    public Fragment getItem(int position) {
        Cates subChannel = mChannelList.get(position);
        RecycleScrollFragment recycleScrollFragment = RecycleScrollFragment.newInstance(subChannel.getId());
        recycleScrollFragment.setCallback(mCallback);
        return recycleScrollFragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return mChannelList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannelList.get(position).getName();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mFragment = (RecycleScrollFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
