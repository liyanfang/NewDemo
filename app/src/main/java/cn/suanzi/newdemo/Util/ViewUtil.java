package cn.suanzi.newdemo.Util;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by liyanfang on 2018/5/2.
 * 描述：view的操作类
 */
public class ViewUtil {
    /**
     * 获取圆形的shape
     * @param bgColor 背景颜色
     * @param width 边宽
     * @param strokeColor 边的颜色
     * @return
     */
    public static GradientDrawable getCircularDrawable(int bgColor, int width, int strokeColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setUseLevel(false);
        gradientDrawable.setSize(1,1);
        gradientDrawable.setColor(bgColor);
        gradientDrawable.setStroke(width, strokeColor);
        return gradientDrawable;
    }

    /**
     * 设置LinearLayout布局view的宽高
     * @param width 控件宽
     * @param height 控件高度
     * @param view 控件
     */
    public static void setLyViwSize(View view, int width, int height) {
        // 计算专场图片的宽高
        if (view != null) {
            LinearLayout.LayoutParams imgParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (width > 0) {
                imgParams.width = width;
            }
            if (height > 0) {
                imgParams.height = height;
            }
            view.setLayoutParams(imgParams);
        }
    }
}
