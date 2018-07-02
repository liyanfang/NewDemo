package cn.suanzi.newdemo.Util;

import android.net.Uri;
import android.provider.Settings;

import cn.suanzi.newdemo.App;

/**
 * Created by liyanfang on 2018/6/14.
 * 描述：
 */
public class ScreenBrightness {

    /**
     * 获得当前屏幕亮度的模式
     *
     * @return 1 为自动调节屏幕亮度,0 为手动调节屏幕亮度,-1 获取失败
     */
    public static int getScreenMode() {
        int mode = -1;
        try {
            mode = Settings.System.getInt(App.getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return mode;
    }

    /**
     * 设置当前屏幕亮度的模式
     *
     * @param mode 1 为自动调节屏幕亮度,0 为手动调节屏幕亮度
     */
    public static void setScreenMode(int mode) {
        try {
            Settings.System.putInt(App.getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
            Uri uri = Settings.System
                    .getUriFor("screen_brightness_mode");
            App.getContext().getContentResolver().notifyChange(uri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获得当前屏幕亮度值
     *
     * @return 0--255
     */
    public static int getScreenBrightness() {
        int screenBrightness = -1;
        try {
            screenBrightness = Settings.System.getInt(App.getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     *
     * @param paramInt 0-255
     */
    public static void setScreenBrightness(int paramInt) {
        Settings.System.putInt(App.getContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, paramInt);
        Uri uri = Settings.System
                .getUriFor("screen_brightness");
        App.getContext().getContentResolver().notifyChange(uri, null);
    }
}
