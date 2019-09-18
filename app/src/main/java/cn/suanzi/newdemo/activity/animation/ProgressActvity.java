package cn.suanzi.newdemo.activity.animation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.RoundBackgroundColorSpan;
import cn.suanzi.newdemo.view.progress.IndicateProgressView;
import cn.suanzi.newdemo.view.progress.SaleProgressView;
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

        TextView textView = findViewById(R.id.tv_name);
        String time = "23 : 14 : 05";
        SpannableString spannableString = new SpannableString(time);
        spannableString.setSpan(new AbsoluteSizeSpan(11, true), 0, time.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new TypefaceSpan("default-bold"), 0, time.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new RoundBackgroundColorSpan(Color.BLACK, Color.WHITE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RoundBackgroundColorSpan(Color.BLACK, Color.WHITE), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RoundBackgroundColorSpan(Color.BLACK, Color.WHITE), 10, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
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

        SaleProgressView saleProgress = findViewById(R.id.sale_progress);
        List<Float> floatList = new ArrayList<>();
        floatList.add(10.f);
        floatList.add(20.f);
        floatList.add(40.f);
        floatList.add(50.f);
        floatList.add(60.f);
        floatList.add(80.f);
        saleProgress.setMax(100, 50, floatList);
    }
}
