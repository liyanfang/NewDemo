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
public class Fragment3 extends BaseFragment {

    private static final String TAG = Fragment3.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3,null);
        if (mFirstFlag) {
            mFirstFlag = false;
            Log.d(TAG, "onCreate onResume: Fragment3");
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFirstFlag) {
            Log.d(TAG, "onCreate onResume: Fragment3");
        }
    }
}
