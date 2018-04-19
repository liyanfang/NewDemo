package cn.suanzi.newdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.suanzi.newdemo.R;

/**
 * Created by liyanfang on 2016/7/16.
 */
public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();
    public boolean mFirstFlag = false;
    public View mView;
    public BaseFragment() {
        mFirstFlag = true;
        Log.d(TAG, "onCreate 构造方法: BaseFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null) {
            initView(mView);
            initData();
        }
        return mView;
    }

    public void initView(View view){};
    public void initData(){};

    /**
     * 点击的时候调用方法调用方法
     */
    public void transferMethod(int position) {

    }


    @Override
    public void onResume() {
        super.onResume();
        if (mFirstFlag) {
            Log.d(TAG, "onCreate onResume: Fragment3");
        }
    }
}
