package cn.suanzi.newdemo.Util;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by liyanfang on 2018/5/11.
 * 描述：线程池
 */
public class ThreadPoolExecutorUtil {

    /**
     * 五个参数的构造函数 int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)
     * 六个参数的构造函数- 1种 (int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory)
     * 六个参数的构造函数- 2种 (int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler)
     * 七个参数的构造函数int （corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
     */
//    private ThreadPoolExecutor mThreadPoolExecutor;
        private ExecutorService mExecutorService;

    private ConcurrentLinkedQueue<String> mEventQueue = new ConcurrentLinkedQueue<>();

    private Future mFuture;

    private int mMaxNum = 20;

    /**
     * corePoolSize 该线程中核心线程数最大值，线程池中的线程数小于corePoolSize时，新建的线程为核心线程，超过的为非核心线程
     */
    public ThreadPoolExecutorUtil() {
        // 单例
//        mExecutorService = Executors.newSingleThreadExecutor();
        // 定长
        mExecutorService = Executors.newFixedThreadPool(3);
        // 缓存线程，线程数量参数达到最大容量
//        mExecutorService = Executors.newCachedThreadPool();
        // 定长，周期性
//        mExecutorService = Executors.newScheduledThreadPool(1);
    }

    public synchronized void start () {
        if (mFuture == null || mFuture.isDone()) {
//            if (mEventQueue.size() >= mMaxNum) {
                mFuture = mExecutorService.submit(new StartRunnable());
//            }
        }
    }

    public ThreadPoolExecutorUtil addEventQueue (String size) {
        mEventQueue.add(size);
        return this;
    }

    /**
     * 线程
     */
    public class StartRunnable implements Runnable {

        @Override
        public void run() {
            while (mEventQueue.size() > 0) {
                String text = mEventQueue.poll();
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "run: " + text);
            }
        }
    }

    public void initThreadPoll() {
        // allCoreThreadTimeOut 为true的时候，核心线程长时间不工作的时候，会自动销毁
//        mThreadPoolExecutor.allowCoreThreadTimeOut(true);
    }
}
