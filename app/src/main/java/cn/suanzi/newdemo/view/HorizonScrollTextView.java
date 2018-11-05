package cn.suanzi.newdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

public class HorizonScrollTextView extends TextView{
	private boolean mStopMarquee;  
    private String mText;  
    private float mCoordinateX;  
    private float mTextWidth;  
   
   
    public HorizonScrollTextView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
   
   
    public void setText(String text) {  
        this.mText = text;  
        mTextWidth = getPaint().measureText(mText);  
        if (mHandler.hasMessages(0))  
            mHandler.removeMessages(0);  
        mHandler.sendEmptyMessageDelayed(0, 2000);  
    }  
   
    
    @Override 
    protected void onAttachedToWindow() {  
        mStopMarquee = false;  
        if (!(mText == null || mText.isEmpty()))  
            mHandler.sendEmptyMessageDelayed(0, 2000);  
        super.onAttachedToWindow();  
    }  
   
   
    @Override 
    protected void onDetachedFromWindow() {  
        mStopMarquee = true;  
        if (mHandler.hasMessages(0))  
            mHandler.removeMessages(0);  
        super.onDetachedFromWindow();  
    }  
   
   
  
    @Override 
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
        if (!(mText == null || mText.isEmpty()))
            // canvas.drawText(text, x, y, paint)，第一个参数是我们需要绘制的文本，
            // 第四个参数是我们的画笔，这两个不用多说，主要是第二和第三个参数的含义，
            // 这两个参数在不同的情况下的值还是不一样的，x默认是这个字符串的左边在屏幕的位置，
            // 如果设置了paint.setTextAlign(Paint.Align.CENTER);那就是字符的中心，
            // y是指定这个字符baseline在屏幕上的位置，大家记住了，不要混淆，y不是这个字符中心在屏幕上的位置，
            // 而是baseline在屏幕上的位置。
            canvas.drawText(mText, mCoordinateX, 30, getPaint());  
    }  
   
    private Handler mHandler = new Handler() {  
        @Override 
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case 0:  
                if (Math.abs(mCoordinateX) > (mTextWidth + 5)) {  
                    mCoordinateX = 0;  
                    invalidate();  
                    if (!mStopMarquee) {  
                        sendEmptyMessageDelayed(0,500);  
                    }  
                } else {  
                    mCoordinateX -= 1;  
                    invalidate();  
                    if (!mStopMarquee) {  
                        sendEmptyMessageDelayed(0, 30);  
                    }  
                }  
                break;  
            }  
            super.handleMessage(msg);  
        }  
    }; 
}
