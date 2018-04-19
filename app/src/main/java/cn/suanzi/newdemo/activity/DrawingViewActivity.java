package cn.suanzi.newdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.View.MyProgressView;

/**
 * 画自动转动的圆环
 */
public class DrawingViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DrawingViewActivity.class.getSimpleName();

    private MyProgressView mTasksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ring);
        initView();
    }

    private void initView() {
        mTasksView = (MyProgressView) findViewById(R.id.flow_prgress_view);
        findViewById(R.id.btn_begin_anim).setOnClickListener(this);
    }

    public void beginAnim(){
        mTasksView.setUsedFlow("200.0M");
        mTasksView.setmShowProgress(100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_begin_anim:
                beginAnim();
                break;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
