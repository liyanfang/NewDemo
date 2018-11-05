package cn.suanzi.newdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.ViewUtil;
import cn.suanzi.newdemo.Util.YxThreadUtil;
import cn.suanzi.newdemo.adapter.BannerAdapter;
import cn.suanzi.newdemo.call.YxBannerCallBack;

/**
 * 滚屏
 * Created by liyanfang on 2016/11/21.
 */
public class YxBanner extends LinearLayout {
    /** 滚动时间 （4秒）*/
    private static final int SCROLL_TIME = 5*1000;
    private static final int SCROLL_NUM = 1 * 100;
    /** topBanner 数据*/
    private List mBannerDataList;
    /** view的集合*/
    private List<ImageView> mImagesList;
    /** 滚屏图上对应的点*/
    private ImageView[] mPoints;
    /** 专题栏*/
    private ViewPager mViewPager;
    /** 整个布局*/
    private FrameLayout mflBannerView;
    /** 滚屏中的点 */
    private LinearLayout mLyGroupViews;
    /** 默认图片*/
    private ImageView mIvDefault;
    /** 图片宽度*/
    private int mImgWidth;
    private int mImgHeight;
    /** 小圆点样式*/
    private GradientDrawable mSelDraw;
    /** 未选中*/
    private GradientDrawable mUnSelDraw;
    /** 间隔时间*/
    private int mDelayTime = SCROLL_TIME;
    /** 点击回调*/
    private YxBannerCallBack mYxCallBack;
    /** 默认背景图*/
    private int mBannerBackgroundImage;
    /** 点的宽高*/
    private int mPointWidth = (int) getResources().getDimension(R.dimen.ttg_dp_6);
    /** 点的高度*/
    private int mPointHeight = (int) getResources().getDimension(R.dimen.ttg_dp_6);

    /**
     * 设置回调
     * @param yxCallBack
     */
    public YxBanner setYxCallBack (YxBannerCallBack yxCallBack) {
        this.mYxCallBack = yxCallBack;
        return this;
    }

    public YxBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
        initView();
    }

    public YxBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
        initView();
    }

    /**
     * 初始化数据
     * @param context 上下文
     * @param attrs
     */
    private void initData (Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.yxBanner);
        mBannerBackgroundImage = typedArray.getResourceId(R.styleable.yxBanner_banner_default_image, R.drawable.no_banner);
        mPointWidth = (int) typedArray.getDimension(R.styleable.yxBanner_sel_indicator_width, (int) getResources().getDimension(R.dimen.ttg_dp_6));
        mPointHeight = (int) typedArray.getDimension(R.styleable.yxBanner_un_sel_indicator_height, (int) getResources().getDimension(R.dimen.ttg_dp_6));
//        int bannerIndicatorGravity = typedArray.getInteger(R.styleable.yxBanner_banner_indicator_gravity, Gravity.LEFT);
//        setIndicatorGravity(bannerIndicatorGravity); // 设置点的位置
        int unSelIndicatorColor = typedArray.getColor(R.styleable.yxBanner_un_sel_indicator_color, Color.GRAY);
        int selIndicatorColor = typedArray.getColor(R.styleable.yxBanner_sel_indicator_color, Color.WHITE);
        mSelDraw = ViewUtil.getCircularDrawable(selIndicatorColor, 0, 0);
        mUnSelDraw = ViewUtil.getCircularDrawable(unSelIndicatorColor, 0, 0);
        typedArray.recycle();
    }

    private void initView () {
        View view = inflate(getContext(), R.layout.ttg_sroll_view, this);
        mLyGroupViews = (LinearLayout)view.findViewById(R.id.ttg_ly_scroll_point);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_img);
        mIvDefault = (ImageView) view.findViewById(R.id.iv_default);
        mIvDefault.setImageResource(mBannerBackgroundImage);
        // 跟布局高度
        mBannerDataList = new ArrayList();
        mImagesList = new ArrayList<ImageView>();
        mflBannerView = (FrameLayout) view.findViewById(R.id.ttg_fy_view_scroll);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置宽高
     * @param width 设置banner的宽
     * @param height 设置banner的高
     */
    public YxBanner setBannerSize (int width, int height) {
        this.mImgWidth = width;
        this.mImgHeight = height;
        if (mflBannerView != null) {
            ViewUtil.setLyViwSize(mflBannerView, mImgWidth, mImgHeight);
        }
        return this;
    }

    /**
     * 设置间隔时间
     * @param delayTime 间隔时间
     * @return
     */
    public YxBanner setDelayTime(int delayTime) {
        this.mDelayTime = delayTime;
        return this;
    }

    /**
     * 设置点是否居中
     * @param position 位置
     */
    public YxBanner setIndicatorGravity (int position) {
        if (mLyGroupViews == null) {
            return this;
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mLyGroupViews.getLayoutParams();
        switch (position) {
            case Gravity.CENTER:
                lp.gravity = Gravity.CENTER | Gravity.BOTTOM;
                break;
            case Gravity.RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                break;
            case Gravity.LEFT :
                lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                break;
            default:
                break;
        }
        mLyGroupViews.setLayoutParams(lp);
        return this;
    }

    /** 开始*/
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startScroll();
    }

    /** 销毁*/
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopScroll();
    }

    /***
     * 停止滚屏
     */
    public void stopScroll () {
        if (mBannerDataList != null && mBannerDataList.size() > 0) {
            YxThreadUtil.stop(mHandler, mScrollRunnable);
        }
    }

    /***
     * 启动滚屏
     */
    public void startScroll () {
        if (mBannerDataList != null && mBannerDataList.size() > 0) {
            mHandler.removeCallbacks(mScrollRunnable);
            YxThreadUtil.start(mHandler, mScrollRunnable, mDelayTime);
        }
    }

    /** 当加载完成xml后，就会执行那个方法*/
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

//    /**
//     * 判断滚屏是否需要更新
//     * @param specialList
//     * @return
//     */
//    private boolean isScrollUpdate (List<Special> specialList) {
//        if (specialList == null) {
//            return false;
//        }
//        boolean isUppFlag = (mBannerDataList != null); // 是否可以更新
//        int mark = 0;
//        if (isUppFlag && specialList.size() == mBannerDataList.size() && mBannerDataList.size() != 0) {
//            isUppFlag = false;
//            for (Special special : specialList) {
//                for (Special originSpecial : mBannerDataList){
//                    if (special != null && originSpecial != null && !TextUtils.isEmpty(special.getId()) && special.getId().equals(originSpecial.getId())) {
//                        mark++;
//                        if (!TextUtils.isEmpty(special.getCoverImg()) && !special.getCoverImg().equals(originSpecial.getCoverImg())) {
//                            isUppFlag = true;
//                        }
//                    }
//                }
//            }
//        }
//        return (mark != specialList.size()) || isUppFlag;
//    }


    /**
     * 准备轮播图列表的数据
     * @param bannerDataList
     */
    private void setOriginSpecialList (List<Object> bannerDataList) {
        if (bannerDataList == null) {
            mIvDefault.setVisibility(VISIBLE);
            return;
        }
        mIvDefault.setVisibility(GONE);
        if (mImagesList != null) {
            if (mImagesList.size() > 0) {
                mImagesList.clear();
            }
        } else {
            mImagesList = new ArrayList<ImageView>();
        }
        // 准备图片
        int length = bannerDataList.size() == 2 ? 2 : 0;
        for (int i = 0; i < bannerDataList.size() + length; i++) {
            int index = (length != 2) ? i : (i % 2 == 0) ? 0 : 1;
            Object bannerObj = bannerDataList.get(index);
            if (bannerObj != null) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                if (mYxCallBack != null) {
                    imageView = mYxCallBack.displayImage(getContext(), bannerObj, imageView);
                    mImagesList.add(imageView);
                }
            }
        }
        if (mBannerDataList.size() > 0) {
            mBannerDataList.clear();
        }
        mBannerDataList.addAll(bannerDataList);
    }

    /**
     * 设置滚屏中的点
     * @param bannerDataList
     */
    private void setScrollPoint (List<Object> bannerDataList) {
        if (bannerDataList.size() < 2) {
            mLyGroupViews.setVisibility(GONE);
            return;
        }
        mLyGroupViews.setVisibility(VISIBLE);
        if (mLyGroupViews != null) {
            mLyGroupViews.removeAllViews();
        }
        mPoints = new ImageView[bannerDataList.size()];
        for (int i = 0; i < mPoints.length; i++) {
            ImageView imageView = new ImageView(getContext());
            LayoutParams params = new LayoutParams(mPointWidth, mPointHeight);
            params.leftMargin = dip2px(4);
            params.rightMargin = dip2px(4);
            imageView.setLayoutParams(params);
            mPoints[i] = imageView;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mPoints[i].setBackground(i == 0 ? mSelDraw : mUnSelDraw);
            } else {
                mPoints[i].setBackgroundDrawable(i == 0 ? mSelDraw : mUnSelDraw);
            }
            if (mLyGroupViews != null) {
                mLyGroupViews.addView(mPoints[i]);
            }
        }
    }

    /**
     * 滚屏方法
     */
    public void show(List specialList) {
        if (specialList != null && specialList.size() > 0) {
            // 准备数据
            setOriginSpecialList(specialList);
            // 适配器
            BannerAdapter mScrollPicAdapter = new BannerAdapter(mImagesList);
            mViewPager.setAdapter(mScrollPicAdapter);
            // 滑动回调监听
            mScrollPicAdapter.setCallBack(yxCallBack);
            // 设置点
            setScrollPoint(specialList);
            mViewPager.addOnPageChangeListener(new MyListener());
            mViewPager.setCurrentItem(0); // 让图片可以循环滚动
            if (mHandler != null && mScrollRunnable != null) {
                mHandler.removeCallbacks(mScrollRunnable);
                mHandler.postDelayed(mScrollRunnable, mDelayTime);
            }
        }
    }

    private YxBannerCallBack yxCallBack = new YxBannerCallBack(){
        @Override
        public void onBannerItemClick(int position) {
            super.onBannerItemClick(position);
                if (mYxCallBack != null) {
                    position = (mBannerDataList.size() <= 2) ? position % 2 : position;
                    mYxCallBack.onBannerItemClick(position);
                }
        }

        @Override
        public void onScrollTouch(boolean isStartRunnable) {
            super.onScrollTouch(isStartRunnable);
            if (mHandler != null && mScrollRunnable != null) {
                if (isStartRunnable) {
                    mHandler.postDelayed(mScrollRunnable, mDelayTime);
                } else {
                    mHandler.removeCallbacksAndMessages(null);
                }
            }
        }
    };

    /**
     * 启动一个线程
     */
    private Runnable mScrollRunnable = new Runnable() {
        @Override
        public void run() {
            /*** 更新界面 **/
            mHandler.obtainMessage().sendToTarget();
            if (mPoints != null && mPoints.length > 0) {
                mHandler.postDelayed(this, mDelayTime);
            }
        }
    };

    /**
     * 切换图片
     * @author ad
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    };

    /**
     * viewPage的改变事件
     * @author ad
     */
    private class MyListener implements ViewPager.OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int arg0) {}

        // 当当前页面被滑动时调用
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            if (mPoints != null && mPoints.length > 1) {
                if (mPoints.length <= 2) {
                    position = position % 2;
                } else if (position > 2) {
                    position = position % mPoints.length;
                }

                for (int i = 0; i < mPoints.length; i++) {
                    if (position != i) {
                        mPoints[i].setBackgroundDrawable(mUnSelDraw);// 没有选中
                    } else {
                        mPoints[position].setBackgroundDrawable(mSelDraw);// 选中了
                    }
                }
            }
        }
    }

    /**
     * dp转px
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        try{
            final float scale = getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Exception e) {
            return (int) dpValue;
        }
    }

}
