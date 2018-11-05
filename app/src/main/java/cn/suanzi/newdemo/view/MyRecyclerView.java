package cn.suanzi.newdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by liyanfang on 2018/7/12.
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float mLastY;
    private float mLastX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = e.getY();
                mLastX = e.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float lastY = e.getY() - mLastY;
                float lastX = e.getX() - mLastX;
                if (Math.abs(lastX) > Math.abs(lastY)) {
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }
}
