package cn.suanzi.newdemo.call;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by liyanfang on 2018/5/2.
 * 描述：
 */
public abstract class YxBannerCallBack {
    /**
     * 滚动触摸
     * @param isStartRunnable 是否启动线程
     */
    public void onScrollTouch(boolean isStartRunnable){}

    /**
     * 点击事件
     * @param position 当前位置 position = (mSpecialList.size() <= 2) ? position % 2 : position;
     */
    public void onBannerItemClick(int position){}

    /**
     * 加载图片
     *
     * @param context   上下文
     * @param path      路径
     * @param imageView 图片
     * @return
     */
    public ImageView displayImage(Context context, Object path, ImageView imageView) {
        return imageView;
    }
}
