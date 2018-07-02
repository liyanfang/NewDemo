package cn.suanzi.newdemo.View;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.Random;

import cn.suanzi.newdemo.R;

import static java.lang.Integer.MAX_VALUE;

/**
 * Created by liyanfang on 2018/4/20.
 * 描述：自定义View之CircleIndicatorView实现圆形指示器
 * Constructor->
 * onFinishInflate->
 * onMeasure..->
 * onSizeChanged->
 * onLayout->
 * addOnGlobalLayoutListener->
 * onWindowFocusChanged->
 * onMeasure->
 * onLayout
 * 由上可知, onMeasure和onLayout会被多次调用.
 */
public class CircleIndicatorView extends View{
    private static final String TAG = CircleIndicatorView.class.getSimpleName();
    ViewPager mViewpager;
    /** 园的颜色*/
    private int circleColor;
    /** 圆环的颜色*/
    private int ringColor;
    /** 圆环的宽度*/
    private int ringWidth;
    /** 字体颜色*/
    private int txtColor;
    /** 字体大小*/
    private int txtSize;
    /** 文字*/
    private String text;
    /** 外环的画笔*/
    private Paint ringPaint = new Paint();
    /** 园的画笔*/
    private Paint circlePaint = new Paint();
    /** 文本的画笔*/
    private Paint txtPaint = new Paint();
    /** 中心位置*/
    private int mYCenter;
    private int mXCenter;

    public CircleIndicatorView(Context context) {
        super(context);
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs); // 初始化值
        initPaint(); // 初始化画笔
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void init (Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicatorView);
        circleColor = ta.getColor(R.styleable.CircleIndicatorView_cirColor, Color.GRAY);
        ringColor = ta.getColor(R.styleable.CircleIndicatorView_rgColor, Color.RED);
        txtColor = ta.getColor(R.styleable.CircleIndicatorView_txtColor, Color.WHITE);
        ringWidth = (int) ta.getDimension(R.styleable.CircleIndicatorView_ringWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics()));
        txtSize = (int) ta.getDimension(R.styleable.CircleIndicatorView_txtSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics()));
        text = ta.getString(R.styleable.CircleIndicatorView_txt);
        ta.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint () {
        ringPaint.setColor(ringColor);
        ringPaint.setAntiAlias(true); // 没有锯齿
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(ringWidth);
        ringPaint.setStrokeCap(Paint.Cap.ROUND); // 俩角绘制弧度

        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE); // 整个园都有颜色

        txtPaint.setAntiAlias(true);
        txtPaint.setTextAlign(Paint.Align.CENTER);
        txtPaint.setColor(txtColor);
        txtPaint.setTextSize(txtSize);

    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        Log.d(TAG, "CircleIndicatorView-test onFinishInflate : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureHeight(heightMeasureSpec);
        measureWidth(widthMeasureSpec);
        Log.d(TAG, "CircleIndicatorView-test onMeasure : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight());
    }

    /**
     * 测量宽度
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth (int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY: // 精确测量 exactly
                result = specSize;
                break;
            case MeasureSpec.AT_MOST: // 最大尺寸 at_most
            case MeasureSpec.UNSPECIFIED: // 未确定 unspecified
                break;
        }
        return result;
    }

    /**
     * 测量高度
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight (int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                bringToFront();
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "CircleIndicatorView-test onSizeChanged : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "CircleIndicatorView-test onLayout : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight());
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d(TAG, "CircleIndicatorView-test onWindowFocusChanged : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight());
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        Log.d(TAG, "CircleIndicatorView-test onWindowVisibilityChanged : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight() + ",visibility = " + visibility );

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "CircleIndicatorView-test onAttachedToWindow : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight());

    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        Log.d(TAG, "CircleIndicatorView-test onFocusChanged : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "CircleIndicatorView-test onDetachedFromWindow : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight());
    }

    private int sweepAngle = 360;
    private int startAngle = -90;
    private int colors[] = {Color.parseColor("#ff2738"),
            Color.parseColor("#22b7e9"),
            Color.parseColor("#8522E9"),
            Color.parseColor("#f4329c"),
            Color.parseColor("#4d11ee"),
            Color.parseColor("#00ff96"),
            Color.parseColor("#ffb200"),
            Color.parseColor("#e8ff00"),
            Color.parseColor("#ebbcb3"),
            Color.parseColor("#00ddff")};
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d(TAG, "CircleIndicatorView-test onDraw : getMeasuredHeight=" + getMeasuredHeight() + ", getHeight: " + getHeight());
        mXCenter = getMeasuredWidth() / 2;
        mYCenter = getMeasuredHeight() / 2;
        int radius = (int) dp(80);
        canvas.drawCircle(mXCenter, mYCenter, radius, circlePaint);

        int mRingRadius = radius + ringWidth / 2;
        int space = (int) dp(25);
        RectF oval = new RectF();
        oval.left = mXCenter - mRingRadius - space;
        oval.top = mYCenter - mRingRadius - space;
        oval.right = mXCenter + mRingRadius + space;
        oval.bottom = mYCenter +  mRingRadius + space;
        canvas.drawArc(oval, startAngle, sweepAngle, false, ringPaint);

        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        int textHeight = (int) (fontMetrics.bottom - fontMetrics.top);
        canvas.drawText(text, getMeasuredWidth() / 2, getMeasuredHeight() / 2 + textHeight / 2 - fontMetrics.bottom, txtPaint);
        // 小圆点
        Paint pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        canvas.translate(getWidth() / 2, getHeight() / 2);//这时候的画布已经移动到了中心位置
        canvas.rotate(getInSweepAngle());
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.iv_picture);
        canvas.translate(0, getHeight() / 2 - getPaddingLeft());

        // 获得图片的宽高
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // 设置想要的大小
        int newWidth = (int) dp(25);
        int newHeight = (int) dp(25);
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap dotBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        int xPoint = (int) (getWidth()/2 - mRingRadius - ringWidth/2 - dp(3));
        int yPoint = (int) (getHeight()/2 - mRingRadius - ringWidth/2 - dp(3));
        canvas.drawBitmap(dotBitmap, -xPoint , -yPoint, pointPaint);
        canvas.rotate(-getInSweepAngle());
        float inSweepAngle = getInSweepAngle() - 360;
        if (inSweepAngle >= 0 && inSweepAngle <= 1) {
            goToPoint(100);
            int color = colors[new Random().nextInt(10)];
            circlePaint.setColor(color);
            canvas.drawCircle(mXCenter, mYCenter, radius, circlePaint);
        }
        invalidate();
    }

    /** 设置待光标的点的位置幅度*/
    private float inSweepAngle;

    public float getInSweepAngle() {
        return inSweepAngle;
    }

    /**
     * 获取dp
     * @param value
     * @return
     */
    private float dp (int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getContext().getResources().getDisplayMetrics());
    }

    public void setInSweepAngle(float inSweepAngle) {
        this.inSweepAngle = inSweepAngle;
    }

    public void goToPoint(float value) {
        float inSweepAngle = sweepAngle * value / 100;
        ValueAnimator angleAnim = ValueAnimator.ofFloat(0f, inSweepAngle);
        angleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentValue = (float) valueAnimator.getAnimatedValue();
                setInSweepAngle(currentValue);
                invalidate();
            }
        });
        int duration = 3000;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setDuration(duration); // setDuration 做一次动画的时间为3秒
        animatorSet.playTogether(angleAnim);
        animatorSet.start();
    }

}
