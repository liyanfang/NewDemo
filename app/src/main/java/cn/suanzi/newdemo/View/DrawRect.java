package cn.suanzi.newdemo.View;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 画矩形
 * Created by liyanfang on 2017/9/22.
 */
public class DrawRect implements drawGraphics {
    /** 画笔*/
    private Paint mPaint;
    private Paint mPaintFooter;

    public DrawRect() {
        mPaint = new Paint();
        mPaintFooter = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        // TODOAuto-generated method stub

        //定义圆角矩形对象
//        Rect（int left,int top,int right,int bottom）
//        left 矩形左上角X坐标值 top 矩形左上角Y坐标值
//        right  矩形右下角X坐标值 bottom 矩形右下角Y坐标值
        RectF rectF1 = new RectF(120,170,370,500);
        RectF rectF2 = new RectF(50,155,100,400);
        RectF rectF3 = new RectF(390,155,440,400);
        RectF rectF4 = new RectF(140,520,200,650);
        RectF rectF5 = new RectF(290,520,350,650);
        RectF rectF6 = new RectF(100,660,180,710);
        RectF rectF7 = new RectF(315,660,395,710);
        mPaint.setAntiAlias(true);
        mPaintFooter.setAntiAlias(true);
        //设置画笔颜色为BLUE
        mPaint.setColor(Color.parseColor("#06B412"));
        mPaintFooter.setColor(Color.parseColor("#A9230F"));

        //方法用于画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawRoundRect(rectF1, 20, 20, mPaint);
        canvas.drawRoundRect(rectF2, 20, 20, mPaint);
        canvas.drawRoundRect(rectF3, 20, 20, mPaint);
        canvas.drawRoundRect(rectF4, 20, 20, mPaint);
        canvas.drawRoundRect(rectF5, 20, 20, mPaint);
        canvas.drawRoundRect(rectF6, 40, 40, mPaintFooter);
        canvas.drawRoundRect(rectF7, 40, 40, mPaintFooter);
    }
}
