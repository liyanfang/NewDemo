package cn.suanzi.newdemo.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.suanzi.newdemo.R;

/**
 * Created by liyanfang on 2018/7/3.
 * 弹框基类
 */

public abstract class BaseDialog extends AlertDialog implements View.OnClickListener {

    protected BaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        initView();
        initData();
        setDialogStyle();
    }

    /**
     * 设置布局id
     */
    protected abstract int setLayoutId();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化控件布局
     */
    protected abstract void initView();

    /**
     * 点击外边界不消失
     *
     * @param isDismiss
     * @return
     */
    public BaseDialog setTouchOutSideDismiss(boolean isDismiss) {
        this.setCanceledOnTouchOutside(isDismiss);// 设置点击屏幕Dialog不消失
        this.setCancelable(isDismiss);
        return this;
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
//            Timber.e(e);
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }

    /**
     * dialog的宽
     *
     * @return
     */
    protected int width() {
        return getScreenWidth(getContext());
    }

    /** 获取屏幕宽度 */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();
    }

    protected int height() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 设置弹框的位置
     *
     * @param window
     * @param gravity Gravity.BOTTOM， Gravity.TOP ……
     */
    protected void setGravity(Window window, int gravity) {
        if (gravity > 0) {
            window.setGravity(gravity);
        }
    }

    /**
     * 设置对话框的样式
     */
    private void setDialogStyle() {
        try {
            Window window = this.getWindow();
            if (window == null) {
                return;
            }
            setGravity(window, 0);
            //获取对话框当前的参数值
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = width();
            int height = height();
            if (height > 0) {
                params.height = height;
            }
            window.setAttributes(params);
            setDialogBackground(R.drawable.dialog_bg_01);
        } catch (Exception e) {
        }
    }


    /**
     * 设置弹框的背景
     */
    protected void setDialogBackground(@DrawableRes int resId) {
        if (this.getWindow() == null) {
            return;
        }
        this.getWindow().setBackgroundDrawableResource(resId);
    }
}
