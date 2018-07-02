package cn.suanzi.newdemo.Util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

import cn.suanzi.newdemo.R;

/**
 * Created by liyanfang on 2017/7/10.
 */
public class MyBehavior extends CoordinatorLayout.Behavior {

    private WeakReference<View>  childeView;
    private WeakReference<View>  dependentView;
    private int heardSize = -1;
    private int minHeard = -1;
    private boolean isScroll;
    private boolean isExpand;
    /**
     * 一定要重写这个构造函数。因为CoordinatorLayout源码中parseBehavior()函数中直接反射调用这个构造函数
     * @param context
     * @param attrs
     */
    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *  重写Behavior的这个方法来确定你依赖哪些View。由于 我们确定是否应用自定义Behavior，如果是就返回true
     * @param parent
     * @param child 是指应用behavior的View
     * @param dependency 触发behavior并与child进行互动的View
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (child != null) {
            childeView = new WeakReference<View>(child);
        }
        Log.d("TTG", "layoutDependsOn: 00000000000000");
        if (dependency != null && dependency instanceof RelativeLayout) {
            Log.d("TTG", "layoutDependsOn: 11111111111111");
            dependentView = new WeakReference<View>(dependency);
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * 用来判断child是否有一个对应的dependency，如果有就返回true，默认情况下返回的是false
     * @param parent
     * @param child
     * @param layoutDirection
     * @return
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
            child.layout(0, 0, parent.getWidth(), (parent.getHeight() - dependentView.get().getHeight()));
        if (heardSize == -1) {
            heardSize = dependentView.get().getHeight();
            minHeard = dependentView.get().findViewById(R.id.ttg_rl_icon).getHeight(); // 设置头部偏移量
            child.setTranslationY(heardSize); // 设置列表Y轴偏移量
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    /**
     * 确定你是否依赖于这个View。CoordinatorLayout会将自己所有View遍历判断。
     如果确定依赖。这个方法很重要。当所依赖的View变动时会回调这个方法。
     * @param parent
     * @param child
     * @param dependency
     * @return
     */

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        View view = dependentView.get();
        float translationY = child.getTranslationY();
        float min = minHeard*1.0f/heardSize;
        float pro = (translationY) / heardSize;
        View child1 = view.findViewById(R.id.ll);
        child1.setPivotY(0);
        child1.setPivotX(0);
        View titleView = dependentView.get().findViewById(R.id.ttg_rl_icon);
        titleView.setPivotY(0);
        titleView.setPivotX(0);
        titleView.setAlpha(1 - pro);
        if (pro<=min+0.1){
            titleView.setAlpha(1);
        }
        Log.d("TTG", "onDependentViewChanged: " + pro);
        if (pro>0.95){
            titleView.setVisibility(View.GONE);
        }else {
            titleView.setVisibility(View.VISIBLE);

        }
        if (pro >= min && pro <= 1) {
            child1.setAlpha(pro);
            if (pro < 0.7) {
                child1.setVisibility(View.GONE);
            } else {
                child1.setVisibility(View.VISIBLE);
            }

            return true;
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

    /**
     * 此方法表示开始滑动，最后一个参数表示的是滑动方向，
     * 并且只有在返回值为true的时候才能出发接下来的操作，
     * 在这里可以进行一些过滤操作，比如值接受垂直方向的滑动等
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * 在onStartNestedScroll返回为true的时候调用，
     * 此时表示CoordinatorLayout已经拦截了滑动，在这里可以做一些滑动初始化的操作
     */
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        clearAnimotor();
        isScroll = false;
    }

    /**
     * 在开始嵌套滑动之前，会执行此操作，dx、dy分别表示用户手指滑动的距离，consumed则表示在操作过程中，消耗掉的滑动距离，例如：
     ```
     consumed[1] = dy;
     ```
     此时表示垂直方向的滑动被全部消耗，将不会被传递到下一步操作，相对应的child，如RecycleView将不能接受的滑动操作，不会进行滑动
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        View view = dependentView.get();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int height = (int) child.getTranslationY();
        if (dy > 0 && height > minHeard) {
            if (height <= heardSize) {
                int h = height - dy;
                int H = (h < minHeard) ? minHeard : h;
                params.height = H;
                view.setLayoutParams(params);
                child.setTranslationY(H);
                consumed[1] = dy;
            }
        }


    }

    /**
     * 此方法在嵌套滑动时候调用，可以多滑动过程进行操作
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyUnconsumed > 0) {
            return;
        }
        View view = dependentView.get();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int height = (int) child.getTranslationY();
        if (dyUnconsumed < 0&&params!=null) {
            int h = height - dyUnconsumed;

            if (h >= 0 &&h<= heardSize) {
                params.height = h;
                view.setLayoutParams(params);
                child.setTranslationY(h);
                if (child instanceof ListView){
                    ListView listView = (ListView) child;
//                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                    layoutParams.height = 0;
//                    view.setLayoutParams(layoutParams);
//                    listView.setViewHeight(recycleView.getEndView(),0); // TODO
                }
            }

        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return onStopDrag(child, velocityY);

    }

    private boolean onStopDrag(View child, float velocityY) {
        int height = dependentView.get().getHeight();
        if (height>minHeard){
            return true;
        }else {
            return false;
        }

    }


    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        return true;
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {

        int height = dependentView.get().getHeight();
        float translationY = childeView.get().getTranslationY();
        if (translationY > height) {
            isExpand = true;
        } else {
            isExpand = false;
        }

        if (isExpand) {
            float pro = ((translationY - height) * 1.0f / heardSize);
            creatExpendAnimator(translationY, height, (int) (500 * pro));
        }


        if (!isScroll && height > minHeard && height < heardSize) {
            childeView.get().setScrollY(0);
            if (height < 0.7 * heardSize) {//上滑
                float pro = (height - minHeard) * 1.0f / (heardSize - minHeard);
                creatAnimation(height, minHeard, (int) (500 * pro));
            } else {//下滑
                float pro = (heardSize - height) * 1.0f / (heardSize - minHeard);
                creatAnimation(height, heardSize, (int) (500 * pro));
            }
            isScroll = true;
        }


    }


    private ValueAnimator animator;

    private void creatAnimation(float start, float end, int duration) {
        clearAnimotor();
        animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                View view = dependentView.get();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) value;
                view.setLayoutParams(params);
                childeView.get().setTranslationY(value);

            }
        });
        animator.setDuration(duration);
        animator.start();


    }

    private void creatExpendAnimator(float start, float end, int duration) {
        clearAnimotor();
        animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
//                View view = dependentView.get();
//                ViewGroup.LayoutParams params = view.getLayoutParams();
//                params.height = (int) value;
//                view.setLayoutParams(params);
                childeView.get().setTranslationY(value);

            }
        });
        animator.setDuration(duration);
        animator.start();
    }


    private void clearAnimotor() {
        if (animator != null) {
            animator.cancel();
        }


        isScroll = false;
    }

}
