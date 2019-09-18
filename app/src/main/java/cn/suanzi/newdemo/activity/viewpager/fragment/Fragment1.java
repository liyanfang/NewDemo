package cn.suanzi.newdemo.activity.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.suanzi.newdemo.R;

public class Fragment1 extends Fragment {

    public static Fragment1 newInstance () {
        Fragment1 fragment1 = new Fragment1();
        Bundle bundle = new Bundle();
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_hight_light, container, false);
        return view;
    }
}
