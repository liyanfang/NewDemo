package cn.suanzi.newdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.suanzi.newdemo.R;

/**
 *
 * Created by liyanfang on 2017/4/18.
 */
public class TestHandlerActivity extends Activity implements View.OnClickListener{

    private TextView mTvHandlerTest;
    private Button mBtnHandlerTest;
    private MyHandlerThread mMyHandlerThread;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_handler);
        mTvHandlerTest = (TextView) findViewById(R.id.tv_handler_test);
        mBtnHandlerTest = (Button) findViewById(R.id.btn_handler_test);
        mBtnHandlerTest.setOnClickListener(this);
        mTvHandlerTest.setText("当前线程 onCreate ：" + Thread.currentThread().getName());
        // 创建一个线程，这个是除主线程外的一个单独线程，handler不是另外开启线程来执行的，还是在主UI线程中
        mMyHandlerThread = new MyHandlerThread("TTG-Handler-Test");
        mMyHandlerThread.start();
        //注意： 这里必须用到handler的这个构造器，因为需要把callback传进去，从而使自己的HandlerThread的handlerMessage来替换掉Handler原生的handlerThread
        mHandler = new Handler(mMyHandlerThread.getLooper(), mMyHandlerThread);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_handler_test:
                mHandler.sendEmptyMessage(11);
                break;
        }
    }


    private class MyHandlerThread extends HandlerThread implements Handler.Callback {

        public MyHandlerThread(String name) {
            super(name);
        }

        public MyHandlerThread(String name, int priority) {
            super(name, priority);
        }

        @Override
        public boolean handleMessage(Message msg) {
            if (mTvHandlerTest != null) {
                String text = mTvHandlerTest.getText().toString() + "\n"
                        + "当期线程：" + Thread.currentThread().getName() + "msg:" + msg.what;
//                mTvHandlerTest.setText(text); UI控件还是得在UI线程中调用
                Log.d("TTG", "handleMessage: " + text);
            }
            return true;
        }
    }

}
