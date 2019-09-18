package cn.suanzi.newdemo.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

import cn.suanzi.newdemo.App;
import cn.suanzi.newdemo.Util.Util;

public class RoundBackgroundColorSpan extends ReplacementSpan {

    private int bgColor;
    private int textColor;

    /** 文字空格两边的间距*/
    private int textWidth = 6;
    /** 圆角弧度*/
    private int radian = 2;

    public RoundBackgroundColorSpan(int bgColor, int textColor) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        // 设置宽度为增加文字宽度
        return ((int) paint.measureText(text, start, end) + Util.dip2px(App.getContext(), textWidth));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int originalColor = paint.getColor();
        paint.setColor(this.bgColor);
        //画圆角矩形背景
        RectF rectF = new RectF(x,
                top,
                x + ((int) paint.measureText(text, start, end) + Util.dip2px(App.getContext(), textWidth)),
                bottom);
        canvas.drawRoundRect(rectF,
                Util.dip2px(App.getContext(), radian),
                Util.dip2px(App.getContext(), radian),
                paint);
        paint.setColor(this.textColor);
        //画文字,两边各增加8dp
        canvas.drawText(text, start, end, x + Util.dip2px(App.getContext(), textWidth/2), y, paint);
        //将paint复原
        paint.setColor(originalColor);
    }

}
