package cn.suanzi.newdemo.activity.viewpager;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 *
 * @author liyanfang
 * @date 2018/9/4
 * @version 1.2.0
 * tab
 */

public class TabEntity implements CustomTabEntity {

    public String title;
    private int selectedIcon;
    private int unSelectedIcon;

    public TabEntity(String title) {
        this.title = title;
    }

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
