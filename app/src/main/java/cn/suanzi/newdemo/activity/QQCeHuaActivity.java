package cn.suanzi.newdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.View.libary.view.SwipeMenu;

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
