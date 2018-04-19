package cn.suanzi.newdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.adapter.GradViewAdapter;

/**
 * Created by liyanfang on 2017/6/14.
 */
public class GradViewActivity extends Activity {

    private GridView mGvView;
    private GradViewAdapter mGradViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_gradview);
        initView();
        initData();
    }

    private void initView () {
        mGvView = (GridView) findViewById(R.id.tv_ttg_gradview);
    }

    private void initData () {
//        mGradViewAdapter
    }
}
