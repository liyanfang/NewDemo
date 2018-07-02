package cn.suanzi.newdemo.Util;

import android.os.Handler;

/**
 * Created by liyanfang on 2018/5/2.
 * 描述：
 */
public class YxThreadUtil {
    /**
     * 启动线程
     * @param handler
     * @param runnable
     * @param time
     */
    public static void start(Handler handler , Runnable runnable, long time) {
        if (handler != null && runnable != null) {
            handler.postDelayed(runnable, time);
        }
    }

    /**
     * 关闭线程
     * @param handler
     * @param runnable
     */
    public static void stop (Handler handler , Runnable runnable) {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
