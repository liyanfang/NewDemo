package cn.suanzi.newdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by liyanfang on 2017/11/30.
 */
public class App extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext () {
        return mContext;
    }
}
