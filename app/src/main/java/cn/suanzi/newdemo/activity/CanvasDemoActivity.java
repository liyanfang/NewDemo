package cn.suanzi.newdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.suanzi.newdemo.View.GameView;

/**
 * 换皮肤
 * Created by liyanfang on 2017/9/21.
 */
public class CanvasDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }
}
