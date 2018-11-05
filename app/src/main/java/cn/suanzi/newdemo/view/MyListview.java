package cn.suanzi.newdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by liyanfang on 2018/5/15.
 * 描述：
 */
public class MyListview extends ListView {
    private static String TAG = MyListview.class.getSimpleName();
    public MyListview(Context context) {
        super(context);
    }

    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isTop;

    private ScrollView mParent;

    public void setParent (ScrollView parent) {
        this.mParent = parent;
    }

    public void setPosition (boolean isTop) {
        this.isTop = isTop;
    }
    private boolean isScrollToBottom;
    public  void setScrollToBottom (boolean isScrollToBottom) {
        this.isScrollToBottom = isScrollToBottom;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "测试  dispatchTouchEvent: " + isScrollToBottom + ", isTop :" + isTop);
        if (isScrollToBottom) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                mLastY = ev.getY();
                this.getParent().requestDisallowInterceptTouchEvent(true);
                return super.dispatchTouchEvent(ev);
            } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                int top = this.getChildAt(0).getTop();
                float py = ev.getY() - mLastY;
                Log.d(TAG, "onTouchEvent-mLastY: " + (ev.getY() - mLastY) + ", top:" + top);
                if (top == 0 && py > 0) {
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    return super.dispatchTouchEvent(ev);
                }
            }
        }
        super.dispatchTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "测试  onInterceptTouchEvent: ListView : " + flag);
        return flag;
    }

    float mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expac = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
