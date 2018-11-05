package cn.suanzi.newdemo.activity.slide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;

import cn.suanzi.newdemo.R;

/**
 * Created by liyanfang on 2018/7/13.
 */

public class DrawerLayoutActivity extends FragmentActivity{

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_drawer);
        initView();
    }

    private void initView () {
        mDrawerLayout = findViewById(R.id.drawer_layout);
    }

    /**
     * 打开关闭侧滑
     */
    public void openDrawerLayout () {
        if (mDrawerLayout == null) {
            return;
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        } else {
            mDrawerLayout.openDrawer(Gravity.START);
        }
    }

    /**
     * 禁止，启动mDrawerLayout 侧滑
     * @param isSlide true  打开手势滑动(LOCK_MODE_UNLOCKED)， false 禁止手势滑动(LOCK_MODE_LOCKED_CLOSED)
     */
    public void showDrawerLayout  (boolean isSlide) {
        if (mDrawerLayout != null) {
            mDrawerLayout.setDrawerLockMode(isSlide ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }
        super.onBackPressed();
    }
}
