package cn.suanzi.newdemo.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

/**
 * 画机器人
 * Created by liyanfang on 2017/9/22.
 * http://www.cnblogs.com/liang646245771/p/4736883.html
 */
public class GameView extends View implements Runnable{

//    /** 声明画笔对象*/
//    private Paint mPaint;
    /** 声明接口类*/
    private drawGraphics mDrawGraphics;

    public GameView(Context context) {
        super(context);
        init();
    }

    /**
     * 初始化布局
     */
    private void init () {
//        // 构建画笔对象
//        mPaint = new Paint();
        // 开启线程
        new Thread(this).start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // 设置画布的背景图
        canvas.drawColor(Color.parseColor("#F4F4F4")); // 测试用的
//        // 清楚锯齿
//        mPaint.setAntiAlias(true);
//        // 设置图形为空心
//        mPaint.setStyle(Paint.Style.STROKE);
        // 绘制空心几何
        mDrawGraphics = new DrawCircle();
        mDrawGraphics.draw(canvas);
        mDrawGraphics = new DrawLine();
        mDrawGraphics.draw(canvas);
        mDrawGraphics = new DrawRect();
        mDrawGraphics.draw(canvas);
    }

    @Override
    public void run() {
// TODOAuto-generated method stub

        while(!Thread.currentThread().isInterrupted()) {
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                // TODO: handle exception
                Thread.currentThread().interrupt();
            }
            //使用postInvalidate 可以直接在线程中更新界面
            postInvalidate();
        }
    }
}
