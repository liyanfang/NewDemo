package cn.suanzi.newdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.activity.viewpager.TableyoutActivity;

/**
 * Created by liyanfang on 2016/7/16.
 */
public class Fragment2 extends BaseFragment {
    private static final String TAG = Fragment2.class.getSimpleName();
    View view ;
    TextView  tvToast;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment2,null);
        if (mFirstFlag) {
            mFirstFlag = false;
            Log.d(TAG, "onCreate onResume: Fragment2");
            initView(view);
        }
        return view;
    }

    public void initView(View view) {
        super.initView(view);
        tvToast = (TextView) view.findViewById(R.id.tv_toast);
        tvToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableyoutActivity.setCurrentItemFragment(0);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFirstFlag) {
            Log.d(TAG, "onCreate onResume: Fragment2");
        }
    }
}
