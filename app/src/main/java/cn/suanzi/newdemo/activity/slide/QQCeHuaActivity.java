package cn.suanzi.newdemo.activity.slide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.libary.view.SwipeMenu;
import cn.suanzi.newdemo.adapter.base.QQCehuaAdapter;

/**
 * 仿照QQ侧滑
 */
public class QQCeHuaActivity extends AppCompatActivity {
    private static final String TAG = QQCeHuaActivity.class.getSimpleName();
    SwipeMenu mMainSwipemenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_swiper);
        mMainSwipemenu = (SwipeMenu) findViewById(R.id.swipe_menu);
        mMainSwipemenu.setFullColor(this, R.color.colorPrimary);
        mMainSwipemenu.setStyleCode(2111);
        initView();
    }

    private void initView () {
        RecyclerView rvList = findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        QQCehuaAdapter qqCehuaAdapter = new QQCehuaAdapter(this, initList());
        rvList.setAdapter(qqCehuaAdapter);
    }

    private List<String> initList () {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("第" + (i + 1) + "条测试数据……");
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        if (mMainSwipemenu.isMenuShowing()) {
            mMainSwipemenu.hideMenu();
        } else {
            super.onBackPressed();
        }
    }
}
