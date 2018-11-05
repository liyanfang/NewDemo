package cn.suanzi.newdemo.view;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liyanfang on 2017/3/13.
 */
public class FitView extends View {
    public FitView(Context context) {
        super(context);
    }
    public FitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FitView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = StatusBarView.getStatusBarHeight(getContext());
            setMeasuredDimension(widthMeasureSpec,statusBarHeight);
        } else {
            setMeasuredDimension(0,0);
        }
    }
}