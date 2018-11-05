package cn.suanzi.newdemo.activity.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.progress.IndicateProgressView;
import cn.suanzi.newdemo.view.progress.TeamProgressView;

/**
 * Created by liyanfang on 2018/8/31.
 * 进度条
 */

public class ProgressActvity extends Activity {

    private ProgressBar progressBar;

    private TeamProgressView indicateProgressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        initView();
    }

    private void initView() {
        indicateProgressView = findViewById(R.id.team_progress);
        indicateProgressView.setProgress(400);

        IndicateProgressView indicateProgress = findViewById(R.id.indicate_progress);
        indicateProgress.setProgress(60);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(20);

        TextView tvName = findViewById(R.id.tv_name);
        tvName.setTextSize(20);
    }
}
