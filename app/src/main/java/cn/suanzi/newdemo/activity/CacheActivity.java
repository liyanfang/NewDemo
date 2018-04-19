package cn.suanzi.newdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.ACache;

/**
 * Created by liyanfang on 2016/7/9.
 */
public class CacheActivity extends Activity {
    private ACache aCache;
    private TextView mTextView;
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==1){
                mTextView.setText("开始保存");
            }else if(msg.what==2){
                initDate();
            }else{
                if(aCache.getAsString("newText")==null){
                    mTextView.setText("没有保存的数据了，重新加载");
                }
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cache_layout);
        mTextView = (TextView) findViewById(R.id.list);
        initAcache();
        initDate();

    }

    private void initDate() {
        String cacheData = aCache.getAsString("newText");// 从缓存中取数据

        if (cacheData != null) {

            mTextView.setText(cacheData );
        } else {// 模拟网络请求数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    aCache.put("newText", "保存3秒", 3);//间数据放到缓存中，保存时间是2秒
                    mHandler.sendEmptyMessage(1);
                    mHandler.sendEmptyMessageDelayed(2, 1000);//验证在保存转态
                    mHandler.sendEmptyMessageDelayed(3, 4000);//验证不在保存转态
                }
            }).start();

        }

    }

    private void initAcache() {
        aCache = ACache.get(this);
        // 默认选择的路径是new File(context.getCacheDir(),// "ACache")
        // String path = getExternalCacheDir().getAbsolutePath();
        // aCache = ACache.get(new File(path));//设置存储路径用于手动清空缓存使用，
    }
}
