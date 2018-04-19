package cn.suanzi.newdemo.webview;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by liyanfang on 2017/11/30.
 */
public class ThreadPoolManager {
    private static final String LOG_TAG = "ThreadPoolManager";
    private static final ThreadPoolManager instance = new ThreadPoolManager();


    private ExecutorService threadPool = Executors.newFixedThreadPool(100);


    public static ThreadPoolManager getInstance() {
        return instance;
    }


    /**
     * @param runnable 不返回执行结果的异步任务
     */
    public void addTask(Runnable runnable) {
        try {
            if (runnable != null) {
                threadPool.execute(runnable);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "", e);
        }
    }


    /**
     * @param callback 异步任务
     * @return 你可以获取相应的执行结果
     */
    public FutureTask addTaskCallback(Callable<Boolean> callback) {
        if (callback == null) {
            return null;
        } else {
            FutureTask futureTask = new FutureTask<>(callback);
            threadPool.submit(futureTask);
            return futureTask;
        }


    }
}
