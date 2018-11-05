package cn.suanzi.newdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.CircleIndicatorView;

public class CircleIndicatorViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CircleIndicatorViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_circle_indicator_view);
        CircleIndicatorView circleIndicatorView = (CircleIndicatorView) findViewById(R.id.circleIndicatorView);
        circleIndicatorView.goToPoint(100);
    }

    @Override
    public void onClick(View v) {
    }
}
