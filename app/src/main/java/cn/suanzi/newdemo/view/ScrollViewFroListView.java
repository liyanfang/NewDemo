package cn.suanzi.newdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by liyanfang on 2017/7/13.
 */
public class ScrollViewFroListView extends ScrollView {
    public ScrollViewFroListView(Context context) {
        super(context);
    }

    public ScrollViewFroListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewFroListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollViewFroListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * //这个是由我们给出的尺寸大小和模式生成一个包含这两个信息的int变量，这里这个模式这个参数，传三个常量中的一个。
         */
        int expendSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expendSpec);
//        Log.d("TTG", "MeasureSpec : " + MeasureSpec.getMode(MeasureSpec.AT_MOST));

    }
}
