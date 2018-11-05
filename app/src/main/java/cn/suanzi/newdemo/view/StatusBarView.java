package cn.suanzi.newdemo.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



/**
 * 设置状态栏的颜色
 * @author liyanfang
 */
public class StatusBarView {

    /** MIUI6.0系统*/
    private final static int MINU = 1;
    /** 魅族系统*/
    private final static int FLYME = 2;
    /** android6.0系统*/
    private final static int ANDROID_6 = 3;
    /** 不更新*/
    private final static int NO_UPP_STATUS = 0;
    /** 更新状态栏颜色*/
    private final static int UPP_STATUS = 4;

    private static StatusBarView statusBarView;

    /**
     * 判断当前手机系统 1 MINU ， 2 FLYME ， ANDROID_6
     */
    private int phoneSys = -1;

    /**
     * 是否改变字体颜色
     */
    private boolean isChangeStatusBarFontColor;

    public int getPhoneSys() {
        return phoneSys;
    }

    public void setPhoneSys(int phoneSys) {
        this.phoneSys = phoneSys;
    }

    public boolean isChangeStatusBarFontColor() {
        return isChangeStatusBarFontColor;
    }

    public void setChangeStatusBarFontColor(boolean changeStatusBarFontColor) {
        isChangeStatusBarFontColor = changeStatusBarFontColor;
    }

    public static StatusBarView getInstance () {
        if (statusBarView == null) {
            statusBarView = new StatusBarView();
        }
        return statusBarView;
    }

    /**
     * 设置状态栏的颜色 （Activity 调用）
     * @param activity 上下问题
     *  @param statusBarBgColor 状态栏颜色
     */
    public static void onSetStatusBarColor (Activity activity, int statusBarBgColor) {
        if (activity != null) {
//            activity.findViewById(R.id.ch_status_view)
            View statusView = null;
            setStatusColor(activity, statusView, statusBarBgColor);
        }
    }

    public static void onSetStatusBarColor (Activity activity, View view, int statusBarBgColor) {
        onSetStatusBarColor(activity, view, statusBarBgColor, false);
    }

    /**
     * 设置状态栏的颜色 (fragment调用)
     * @param activity 上下问题
     * @param statusBarBgColor 状态栏颜色
     */
    public static void onSetStatusBarColor (Activity activity, View view, int statusBarBgColor, boolean isChangeFontColor) {
        if (activity != null && view != null) {
//            View statusView = view.findViewById(R.id.ch_status_view);
            View statusView = null;
            setStatusColor(activity, statusView, statusBarBgColor);
        }
    }

    /**
     * 设置状态栏颜色
     * @param activity 上下文
     * @param statusView 标题的view
     * @param statusBarBgColor  状态栏颜色
     */
    private static void setStatusColor(Activity activity, View statusView, int statusBarBgColor) {
        if (statusView == null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            setStatusViewColor(activity, statusView, statusBarBgColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setStatusViewColor(activity, statusView, statusBarBgColor);
        } else {
            statusView.setVisibility(View.GONE);
        }
        StatusBarLightMode(activity);
    }

    /**
     * 设置状态栏颜色
     * @param activity 上下文
     * @param statusView 状态栏view
     * @param  statusBarBgColor 状态栏颜色
     */
    private static void setStatusViewColor (Activity activity, View statusView, int statusBarBgColor) {
        statusView.setVisibility(View.VISIBLE);
        if (StatusBarView.getInstance().phoneSys == -1) {
            StatusBarLightMode(activity);
        } else {
            StatusBarLightMode(activity, StatusBarView.getInstance().phoneSys);
        }
        statusView.setBackgroundColor(statusBarBgColor);
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                setAndroid6StatusBar(window, dark);
                if (dark) {
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result=true;
            }catch (Exception e){
            }
        }
        return result;
    }

    /**
     * 设置6.0后的状态颜色
     * @param window 当前窗体
     * @param isUpdateFontColor 是否改变颜色
     */
    private static void setAndroid6StatusBar(Window window, boolean isUpdateFontColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && window != null) {
            if (isUpdateFontColor) {
                window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }

    /**
    * 设置状态栏图标为深色和魅族特定的文字风格
    * 可以用来判断是否为Flyme用户
    * @param window 需要设置的窗口
    * @param dark 是否把状态栏字体及图标颜色设置为深色
    * @return  boolean 成功执行返回true
    *
    */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     *设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int StatusBarLightMode(Activity activity){
        if (!StatusBarView.getInstance().isChangeStatusBarFontColor) {
            StatusBarView.getInstance().setPhoneSys(UPP_STATUS);
            return UPP_STATUS;
        }
        int result = NO_UPP_STATUS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(MIUISetStatusBarLightMode(activity.getWindow(), true)){
                result = MINU;
            } else if(FlymeSetStatusBarLightMode(activity.getWindow(), true)){
                result = FLYME;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 适配浅色状态栏深色字体的时候发现底层版本为Android6.0.1的MIUI7.1系统不支持,所以暂时只支持6.0的
                result = ANDROID_6;
                setAndroid6StatusBar(activity.getWindow(), true);
            }
        }
        StatusBarView.getInstance().setPhoneSys(result);
        return result;
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity
     * @param type 1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode(Activity activity, int type){
        switch (type) {
            case MINU:
                MIUISetStatusBarLightMode(activity.getWindow(), true);
                break;
            case FLYME:
                FlymeSetStatusBarLightMode(activity.getWindow(), true);
                break;
            case ANDROID_6:
                setAndroid6StatusBar(activity.getWindow(), true);
                break;
        }
    }

    /**
     * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
     */
    public void clearStatusBarColor(Activity activity){
//        sIsChangeFontColor = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(MIUISetStatusBarLightMode(activity.getWindow(), false) ){
            } else if(FlymeSetStatusBarLightMode(activity.getWindow(), false)){
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 适配浅色状态栏深色字体的时候发现底层版本为Android6.0.1的MIUI7.1系统不支持,所以暂时只支持6.0的
                setAndroid6StatusBar(activity.getWindow(), false);
            }
        }



    }
    /**
     * 得到状态栏的高度
     * @param context
     * @return 高度（int类型）
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        if (context != null) {
            int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                result = context.getResources().getDimensionPixelOffset(resId);
            }
        }
        return result;
    }
}