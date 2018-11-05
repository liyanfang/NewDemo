package cn.suanzi.newdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.Callback;
import cn.suanzi.newdemo.Util.Util;


/**
 *	滑动指示器pstsindicatorcolor颜色
 *	在视图的底部的全宽度的线pstsunderlinecolor颜色
 *	选项卡之间的分隔pstsdividercolor颜色
 *	滑动指示器pstsindicatorheightheight
 *	在视图的底部的全宽度的线pstsunderlineheight高度
 *	pstsdividerpadding顶部和底部填充的分频器
 *	pststabpaddingleftright左、右填充每个选项卡
 *	pstsscrolloffset卷轴被选择的标签的偏移
 *	pststabbackground背景绘制的每个标签，应该是一个statelistdrawable
 *	pstsshouldexpand如果设置为TRUE，每个标签都给予同样的重量，默认为false
 *	pststextallcaps如果为真，所有选项卡标题都是大写，默认为true
 * 扩展4个属性 ， 分别是 默认的字体大小 和颜色 ，和选中后的 字体大小和颜色
 */
public class TtgPagerSlidingTab extends HorizontalScrollView {
    private final static int TAB_TYPEFACESTYLE = Typeface.NORMAL;

    public interface IconTabProvider {
        int getPageIconResId(int position);
    }

    // @formatter:off
    private static final int[] ATTRS = new int[] {
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    // @formatter:on 扩张
//    private LinearLayout.LayoutParams defaultTabLayoutParams;
//    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    /** 滑动整个容器*/
    private LinearLayout tabsContainer;
    private ViewPager pager;
    private int tabCount;
    private int currentPosition = 0;
    private int selectedPosition = 0;
    private float currentPositionOffset = 0f;
    /** 底线的画笔*/
    private Paint rectPaint;
    /** 分割线的画笔*/
    private Paint dividerPaint;
    /** 滑动指示器颜色*/
    private int indicatorColor = 0xFF666666;
    /** 在视图的底部的全宽度的线pstsunderlinecolor颜色*/
    private int underlineColor = 0x1A000000;
    /** 选项卡之间的分隔pstsdividercolor颜色*/
    private int dividerColor = 0x1A000000;
    /** pstsshouldexpand如果设置为TRUE，每个标签都给予同样的重量，默认为false*/
    private boolean shouldExpand = false;
    /** pststextallcaps如果为真，所有选项卡标题都是大写，默认为true*/
    private boolean textAllCaps = true;
    private int scrollOffset = 52;
    /** 滑动指示器的高度*/
    private int indicatorHeight = 8;
    /** 在视图的底部的全宽度的线高度*/
    private int underlineHeight = 2;
    /** 顶部和底部填充的分频器*/
    private int dividerPadding = 12;
    /** 左、右填充每个选项卡25*/
    private int tabPadding = 0;
    /** 分割线的宽度*/
    private int dividerWidth = 1;
    /** tab中默认字体的大小12*/
    private int tabTextSize = 14;
    /** tab中默认字体的颜色*/
    private int tabTextColor = 0xFF666666;
//    private int tabTextColor = Color.parseColor("#9B30FF");
    /** tab中选中字体的大小*/
//    private int selectedTabTextSize = 14;
    /** tab中选中字体的颜色*/
    private int selectedTabTextColor = 0xFF666666;
    /** 字体*/
    private Typeface tabTypeface = null;
    private int lastScrollX = 0;
//    /** 背景样式*/
//    private int tabBackgroundResId = R.drawable.background_tab;
    /** 本地时间*/
    private Locale locale;
    /** viewPage滑动会调事件*/
    private Callback mCallback;
    /**第一个栏目左边padding 默认20dp  */
    private int mOneCateLeftPadding = 13;
    /**是否显示栏目图片*/
    private  boolean isShowPic;

    public void setShowPic(boolean showPic) {
        isShowPic = showPic;
    }

    public void setmOneCateLeftPadding(int mOneCateLeftPadding) {
        this.mOneCateLeftPadding = mOneCateLeftPadding;
    }

    public TtgPagerSlidingTab(Context context) {
        this(context, null);
    }

    public TtgPagerSlidingTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TtgPagerSlidingTab(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false); // 就是设置将不绘画吗
        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        addView(tabsContainer);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 获取资源种设置的参数
        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, ATTRS);
        typedArray.recycle();
        // get custom attrs
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TtgPagerSlidingTab);
        indicatorColor = typedArray.getColor(R.styleable.TtgPagerSlidingTab_ttgPstsIndicatorColor, indicatorColor);
        //下面4个扩展的属性设置默认tab文字的大小
        tabTextSize = typedArray.getDimensionPixelSize(R.styleable.TtgPagerSlidingTab_ttgZmsTabTextSize, tabTextSize);
        tabTextColor = typedArray.getColor(R.styleable.TtgPagerSlidingTab_ttgZmsTabTextColor, tabTextColor);
        //设置tab选中文字的大小
        selectedTabTextColor = typedArray.getColor(R.styleable.TtgPagerSlidingTab_ttgZmsSelectedTabTextColor, indicatorColor);
        // 获取设置的参数
        underlineColor = typedArray.getColor(R.styleable.TtgPagerSlidingTab_ttgPstsUnderlineColor, underlineColor);
        dividerColor = typedArray.getColor(R.styleable.TtgPagerSlidingTab_ttgPstsDividerColor, dividerColor);
        indicatorHeight = typedArray.getDimensionPixelSize(R.styleable.TtgPagerSlidingTab_ttgPstsIndicatorHeight, indicatorHeight);
        underlineHeight = typedArray.getDimensionPixelSize(R.styleable.TtgPagerSlidingTab_ttgPstsUnderlineHeight, underlineHeight);
        dividerPadding = typedArray.getDimensionPixelSize(R.styleable.TtgPagerSlidingTab_ttgPstsDividerPadding, dividerPadding);
        tabPadding = typedArray.getDimensionPixelSize(R.styleable.TtgPagerSlidingTab_ttgPstsTabPaddingLeftRight, tabPadding);
        shouldExpand = typedArray.getBoolean(R.styleable.TtgPagerSlidingTab_ttgPstsShouldExpand, shouldExpand);
        scrollOffset = typedArray.getDimensionPixelSize(R.styleable.TtgPagerSlidingTab_ttgPstsScrollOffset, scrollOffset);
        textAllCaps = typedArray.getBoolean(R.styleable.TtgPagerSlidingTab_ttgPstsTextAllCaps, textAllCaps);
        //在TypedArray后调用recycle主要是为了缓存。当recycle被调用后，这就说明这个对象从现在可以被重用了。TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，这样就不用每次使用前都需要分配内存
        typedArray.recycle();
        // 底线的画笔
        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.FILL);
        // 左右分割线的画笔
        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);
        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    /**
     * 设置viewPage
     * @param pager
     */
    public void setViewPager(ViewPager pager) {
//        this.mOneCateLeftPadding= TtgTitleBar.getInstance().getBackIconLeftPadding()+Util.px2dip(getContext(),backIconWidth)-15;
//        this.mOneCateLeftPadding= Util.px2dip(getContext(),backIconWidth)-15;
        this.pager = pager;
        if (pager == null || pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        pager.setOnPageChangeListener(pageListener);
            notifyDataSetChanged();

    }

    public void notifyDataSetChanged() {
        tabsContainer.removeAllViews();
        tabCount = pager.getAdapter().getCount();
        for (int i = 0; i < tabCount; i++) {
            if (pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
            } else {
                if(isShowPic){
                    addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
                }else{
                    addNoPicTextTab(i, pager.getAdapter().getPageTitle(i).toString());
                }
            }
        }
        if(isShowPic){
            updateTabStyles();
        }else{
            updateNoPicTabStyles();
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (pager != null) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    currentPosition = pager.getCurrentItem();
                    scrollToChild(currentPosition, 0);
                }
            }
        });
    }

    /**
     * 改变没有图片的方法
     */
    private void updateNoPicTabStyles() {
        for (int i = 0; i < tabCount; i++) {
            View childView = tabsContainer.getChildAt(i);
            if (childView != null && childView instanceof TextView) {
                TextView tab = (TextView) childView;
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                tab.setTypeface(tabTypeface, TAB_TYPEFACESTYLE);
                tab.setTextColor(tabTextColor);
                // setAllCaps() is only available from API 14, so the upper case is made manually if we are on a
                // pre-ICS-build
                if (textAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(locale));
                    }
                }
                // 选中字体的颜色
                if (i == selectedPosition) {
                    tab.setTextColor(selectedTabTextColor);
//                    tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, selectedTabTextSize);
                }
            }
        }
    }

    /**
     * 没有图片的
     * @param position
     * @param title
     */
    private void addNoPicTextTab(int position, String title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        addTab(position, tab);// 添加Tab
    }

    /**
     * 添加文字
     * @param position 位置
     * @param title 文字
     */
    private void addTextTab(final int position, String title) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams( Util.dip2px(getContext(), 20), Util.dip2px(getContext(), 20));
        imageView.setLayoutParams(imageParams);
        linearLayout.addView(imageView);
        TextView textView = new TextView(getContext());
        textView.setText(title);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setGravity(Gravity.CENTER);
        tvParams.topMargin=Util.dip2px(getContext(),3);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setSingleLine();
        textView.setTextSize(14);
        textView.setLayoutParams(tvParams);
        linearLayout.addView(textView);
        addTab(position, linearLayout);// 添加Tab
    }

    /**
     * 给tab添加图标
     * @param position 位置
     * @param resId 资源ID
     */
    private void addIconTab(final int position, int resId) {
        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);
        addTab(position, tab); // 添加Tab
    }

    /**
     * 添加Tab
     * @param position 位置
     * @param tab 视图
     */
    private void addTab(final int position, View tab) {
        if (pager == null) {
            return;
        }
       LinearLayout.LayoutParams defaultTabLayoutParams1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) {
                if (mCallback != null) mCallback.setOnClickListener(position); // 这个必须返回在pager.setCurrentItem() 方法之前，因为要判断点击前和现在点击的位置是否是同一个
            }
        });

        int count = 0;
        if(pager!=null){
            count=pager.getAdapter().getCount();
        }
        if(position == 0){
            tab.setPadding(Util.dip2px(getContext(),mOneCateLeftPadding), 0, Util.dip2px(getContext(),12.50f), 0);
        } else if(count == (position + 1)){
            tab.setPadding(Util.dip2px(getContext(),12.50f), 0, Util.dip2px(getContext(),mOneCateLeftPadding), 0);
        } else {
            tab.setPadding(Util.dip2px(getContext(),12.50f), 0, Util.dip2px(getContext(),12.50f), 0);
        }
        tabsContainer.addView(tab, position,  defaultTabLayoutParams1);
    }



    /**
     * 更新Tab得样式
     */
    private void updateTabStyles() {
        for (int i = 0; i < tabCount; i++) {
            View childView = tabsContainer.getChildAt(i);
            if (childView != null && childView instanceof LinearLayout) {
                TextView tab= (TextView) ((LinearLayout) childView).getChildAt(1);
                tab.setTextSize(12);
                tab.setTypeface(tabTypeface, TAB_TYPEFACESTYLE);
                tab.setTextColor(Color.parseColor("#000000"));
                if (textAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(locale));
                    }
                }
                if (i == selectedPosition) {
                    tab.setTextColor(selectedTabTextColor);
                }
            }
        }

    }

    /**
     * @param position 位置
     * @param offset 便宜
     */
    private void scrollToChild(int position, int offset) {
        if (tabCount == 0 || tabsContainer == null){
            return;
        }
        View childView = tabsContainer.getChildAt(position);
        if (childView != null) {
            int newScrollX = childView.getLeft() + offset;
            if (position > 0 || offset > 0) {
                newScrollX -= scrollOffset;
            }
            if (newScrollX != lastScrollX) {
                lastScrollX = newScrollX;
                scrollTo(newScrollX, 0);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || tabCount == 0 || rectPaint == null || dividerPaint == null){
            return;
        }
        final int height = getHeight();
        // draw underline
        rectPaint.setColor(underlineColor);
        canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);
        // draw indicator line
        rectPaint.setColor(indicatorColor);
        // default: line below current tab
        View currentTab = tabsContainer.getChildAt(currentPosition);
        if (currentTab != null) {
            int count=pager.getAdapter().getCount();
            int tabPadding = Util.dip2px(getContext(),8); // 设置底线左右的宽度25 6
//            float lineLeft = currentTab.getLeft() + tabPadding;
            float lineLeft = 0;
            float lineRight = 0;
            if(currentPosition==0){
                lineLeft = currentTab.getLeft() +Util.dip2px(getContext(),13);
                lineRight = currentTab.getRight() - Util.dip2px(getContext(),12.50f);
            } else if(currentPosition==count-1){
                lineLeft = currentTab.getLeft() +Util.dip2px(getContext(),12.50f);
                lineRight = currentTab.getRight()-Util.dip2px(getContext(),mOneCateLeftPadding);
            }else{
                lineLeft = currentTab.getLeft() +Util.dip2px(getContext(),13);
                lineRight = currentTab.getRight() -Util.dip2px(getContext(),13);
            }
           
            // if there is an offset, start interpolating left and right coordinates between current and next tab
            if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
                View nextTab = tabsContainer.getChildAt(currentPosition + 1);
                if (nextTab != null) {
//                    final float nextTabLeft = nextTab.getLeft() + tabPadding;
                     float nextTabLeft=0f;
                     float nextTabRight=0f;
                    if(currentPosition==0){
                        nextTabLeft = nextTab.getLeft() +Util.dip2px(getContext(),13);
                        nextTabRight = nextTab.getRight()  - Util.dip2px(getContext(),12.50f);
                    }else if(currentPosition==count-2){
                        nextTabLeft = nextTab.getLeft() +Util.dip2px(getContext(),12.50f);
                        nextTabRight = nextTab.getRight()-Util.dip2px(getContext(),mOneCateLeftPadding);
                    }else{
                        nextTabLeft = nextTab.getLeft() +Util.dip2px(getContext(),12.50f);
                        nextTabRight = nextTab.getRight()-Util.dip2px(getContext(),12.50f);
                    }
                    lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
                    lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
                }
            }
            canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
            // draw divider
            dividerPaint.setColor(dividerColor);
            for (int i = 0; i < tabCount - 1; i++) {
                View tab = tabsContainer.getChildAt(i);
                if (tab != null){
                    canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
                }
            }
        }
    }

    private class PageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;
            View childView = tabsContainer.getChildAt(position);
            if (childView != null) {
                scrollToChild(position, (int) (positionOffset * childView.getWidth()));
                invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }
        }

        @Override
        public void onPageSelected(int position) {
            selectedPosition = position;
            if(isShowPic){
                updateTabStyles();
            }else{
                updateNoPicTabStyles();
            }
            if (mCallback != null) {
                mCallback.setPageSelected(position);
            }
        }
    }

    /**
     * 滑动事件的监听
     * @param callback 监听
     */
    public void setOnPageSelectedLister (Callback callback) {
        this.mCallback = callback;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public int getTextColor() {
        return tabTextColor;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        try {
            SavedState savedState = (SavedState) state;
            super.onRestoreInstanceState(savedState.getSuperState());
            currentPosition = savedState.currentPosition;
            requestLayout();
        }catch (Exception e) {
            state = null;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;
        public SavedState(Parcelable superState) {
            super(superState);
        }
        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    /**
     * 设置底部滑动条的颜色
     * @param pstsIndicatorColor 底部滑动条的颜色
     */
    public void setPstsIndicatorColor (int pstsIndicatorColor) {
        this.indicatorColor = pstsIndicatorColor;
    }

    /**
     * 设置选中字体颜色
     * @param selectedTabTextColor 选中字体颜色
     */
    public void setSelectedTabTextColor (int selectedTabTextColor) {
        this.selectedTabTextColor = selectedTabTextColor;
    }
}
