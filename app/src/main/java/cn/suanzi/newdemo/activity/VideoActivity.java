package cn.suanzi.newdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.SeekBar;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.ScreenBrightness;

/**
 * 轮播图
 */
public class VideoActivity extends AppCompatActivity {
    private static final String TAG = VideoActivity.class.getSimpleName();

    AppCompatSeekBar mSeekBar;

    private int mScreenBrightness;

    private int screenMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_video);
        mSeekBar = (AppCompatSeekBar) findViewById(R.id.seek_bar);
        mScreenBrightness = ScreenBrightness.getScreenBrightness();
        mSeekBar.setProgress(mScreenBrightness);
        screenMode = ScreenBrightness.getScreenMode();
        if (screenMode == 1) {
            ScreenBrightness.setScreenMode(0);
        }
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mScreenBrightness = progress;
                ScreenBrightness.setScreenBrightness(mScreenBrightness);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenBrightness.setScreenMode(screenMode);
    }
}
