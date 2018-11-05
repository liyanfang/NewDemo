package cn.suanzi.newdemo.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 画圆
 * Created by liyanfang on 2017/9/22.
 */
public class DrawCircle implements drawGraphics {

    /** 画笔*/
    private Paint mPaint;
    /** 画眼睛*/
    private Paint mPaintEye;

    public DrawCircle() {
        mPaint = new Paint();
        mPaintEye = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        // 绘制圆形(圆心x，圆心y，半径r，画笔p)
        mPaint.setAntiAlias(true);
        mPaintEye.setAntiAlias(true);
        // 都是用于表示坐标系中的一块矩形区域，并可以对其做一些简单操作。这块矩形区域，需要用左上和右下两个坐标点表示。
        RectF rectF = new RectF(120, 60, 370, 240);
        mPaint.setColor(Color.RED);
        mPaintEye.setColor(Color.BLACK);
        // 第一个参数oval为RectF类型，即圆弧显示区域，
        // startAngle和sweepAngle均为float类型，分别表示圆弧起始角度和圆弧度数,3点钟方向为0度，
        // useCenter设置是否显示圆心，boolean类型，
        // paint为画笔
        canvas.drawArc(rectF, 180, 180, true, mPaint);
        // 先画两只眼睛 (圆形)
        // drawCircle 方法用于画圆，前两个参数代表圆心坐标，第三个参数为圆半径，第四个参数是画笔；
        canvas.drawCircle(190, 110, 18, mPaintEye);
        canvas.drawCircle(300, 110, 18, mPaintEye);
        canvas.drawCircle(120, 40, 20, mPaint);
        canvas.drawCircle(379, 40, 20, mPaint);
    }
}
