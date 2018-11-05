package cn.suanzi.newdemo.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 画线
 * Created by liyanfang on 2017/9/22.
 */
public class DrawLine implements drawGraphics {

    /** 画笔*/
    private Paint mPaint;

    public DrawLine() {
        mPaint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        // 绘画直线
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        // 设置绘制的直线粗细
        mPaint.setStrokeWidth(12);
        // 前四个参数的类型均为float，最后一个参数类型为Paint。
        // 表示用画笔paint从点（startX,startY）到点（stopX,stopY）画一条直线；
        canvas.drawLine(120, 40, 170, 90, mPaint);
        canvas.drawLine(320, 90, 370, 40, mPaint);

    }
}
