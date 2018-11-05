package cn.suanzi.newdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by liyanfang on 2018/5/15.
 * 描述：
 */
public class MyScrollView extends ScrollView {
    private static String TAG = MyScrollView.class.getSimpleName();

    private OnScrollToBottomListener mOnScrollToBottomListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 为什么ACTION_DOWN除外？通过上述代码我们不难发现。如果事件是ACTION_DOWN，
     * 那么ViewGroup会重置FLAG_DISALLOW_INTERCEPT标志位并且将mFirstTouchTarget 设置为null。
     * 对于mFirstTouchTarget 我们可以先这么理解，如果事件由子View去处理时mFirstTouchTarget 会被赋值并指向子View。
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "测试  onInterceptTouchEvent: MyScrollView : ");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) { // action_down requestDisallowInterceptTouchEvent 是忽略的不拦截，
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){
        super.onScrollChanged(l,t,oldl,oldt);
        // 滑动的距离加上本身的高度与子View的高度对比
        if(t + getHeight() >=  getChildAt(0).getMeasuredHeight()){
            // ScrollView滑动到底部
            if(mOnScrollToBottomListener != null) {
                mOnScrollToBottomListener.onScrollToBottom();
            }
        } else {
            if(mOnScrollToBottomListener != null) {
                mOnScrollToBottomListener.onNotScrollToBottom();
            }
        }
    }

    public void setScrollToBottomListener(OnScrollToBottomListener listener) {
        this.mOnScrollToBottomListener = listener;
    }

    public interface OnScrollToBottomListener {
        void onScrollToBottom();
        void onNotScrollToBottom();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isflag = super.onTouchEvent(ev);
        Log.d(TAG, "测试  onTouchEvent: MyScrollView : " + isflag);
        return isflag;
    }
}
