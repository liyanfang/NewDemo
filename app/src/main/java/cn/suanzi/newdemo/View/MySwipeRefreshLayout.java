package cn.suanzi.newdemo.View;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.suanzi.newdemo.Util.Util;

/**
 * Created by liyanfang on 2018/5/15.
 * 描述：
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    /** 屏幕一半的宽度*/
    private int windonWidthHalf;

    public MySwipeRefreshLayout(Context context) {

        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init (Context context) {
        windonWidthHalf = Util.getWindowWidth(context);
    }

    private float mMotionX;
    private float mMotionY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMotionX = ev.getX();
                mMotionY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float y = ev.getY();
                float dx = Math.abs((x - mMotionX));
                float dy = Math.abs(y - mMotionY);
                if (dx > dy) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
