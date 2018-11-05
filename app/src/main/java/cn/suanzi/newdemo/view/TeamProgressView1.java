package cn.suanzi.newdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.suanzi.newdemo.R;

/**
 * Created by liyanfang on 2018/11/1.
 * 组团进度条
 */

public class TeamProgressView1 extends View {

    /** 最大值*/
    private float max = 100;
    /** 当前进度*/
    private float progress;
    /** 动画需要的*/
    private float progressCount;
    /** 进度与最大值的比例*/
    private float ratio;
    /** 刻度占比*/
    private float scaleRatio;
    /** 文字颜色*/
    private int textColor;
    /** 边框粗细*/
    private float sideWidth;
    /** 进度条的顶部位置*/
    private float sideTop;
    /** 底部刻度数字top的尺寸*/
    private float scaleBottomNumTop;
    /** 边框所在的矩形*/
    private Paint sidePaint;
    /** 图品的画笔*/
    private Paint srcPaint;
    /** 进度条的高度*/
    private int progressHeight;
    /** 进度条背景的圆角*/
    private float radius;
    /** 进度条的幅度*/
    private float progressRadius;
    /** 整个view 的宽高*/
    private int width;

    private PorterDuffXfermode mPorterDuffXfermode;
    /** 进入框文字大小*/
    private float textSize;
    /** 刻度线文字大小*/
    private float scaleSize;
    /** 左右间距*/
    private int paddingWidth = dp2px(22);

    private Paint textPaint;
    private float baseLineY;
    private Bitmap bgBitmap;
    private boolean isNeedAnim;
    /** 进入提示框的宽高*/
    private float mProgressHintWidth, mProgressHintHeight;
    /** 刻度线*/
    private List<Float> scaleList = new ArrayList<>();

    private float[] radiusArray = { 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f };

    public TeamProgressView1(Context context) {
        this(context, null);
    }

    public TeamProgressView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TeamProgressView1);
//        textColor = ta.getColor(R.styleable.TeamProgressView1_teamTextColor,0xfffd7303);
//        sideWidth = ta.getDimension(R.styleable.TeamProgressView1_teamSideWidth,dp2px(2));
//        radius = ta.getDimension(R.styleable.TeamProgressView1_teamRadius, dp2px(12));
//        progressRadius = ta.getDimension(R.styleable.TeamProgressView1_teamProgressRadius,dp2px(10));
//        progressHeight = (int) ta.getDimension(R.styleable.TeamProgressView1_teamProgressHeight, dp2px(35));
//        textSize = ta.getDimension(R.styleable.TeamProgressView1_teamTextSize,sp2px(10));
//        scaleSize = ta.getDimension(R.styleable.TeamProgressView1_teamScaleSize,sp2px(12));
//        scaleBottomNumTop = ta.getDimension(R.styleable.TeamProgressView1_teamBottomNumTop,dp2px(10));
        ta.recycle();

        setRadius(0, 0, radius, radius);
    }


    private void initPaint() {
        sidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sidePaint.setStyle(Paint.Style.FILL);
        sidePaint.setStrokeWidth(dp2px(1));
        sidePaint.setColor(Color.parseColor("#F29708"));

        srcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font/DIN-Bold.otf");
        textPaint.setTypeface(font);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        sideTop = sideWidth;
//        if (textSrc == null) {
////            textSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_progress_hint);
//            mProgressHintWidth = textSrc.getWidth();
//            mProgressHintHeight = textSrc.getHeight();
//            sideTop = sideTop + mProgressHintHeight;
//            progressHeight = (int) (progressHeight + mProgressHintHeight);
//        }
        // 设置四个角的圆角半径
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取View的宽高
        width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (baseLineY == 0) {
            Paint.FontMetricsInt fm = textPaint.getFontMetricsInt();
            baseLineY = height / 2 - (fm.descent / 2 + fm.ascent / 2);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if(!isNeedAnim){
            progressCount = progress;
        }
        // 计算进度概率
        if (max == 0 || progressCount == 0) {
            ratio = 0.0f;
        } else {
            float currProgress = (progressCount < 3 && progressCount > 0) ? 3f : progress;
            ratio = (float) (Math.log(currProgress) / Math.log(max));
        }
        // 计算刻度占比
        for (int j = 0; j < scaleList.size(); j++) {
            float value = scaleList.get(j);
            if (progressCount <= value) {
                scaleRatio = (float)(j + 1)/(float)scaleList.size();
                break;
            }
        }

//        drawProgressText(canvas);
        drawBg(canvas);
//        drawFg(canvas);
//        drawProgressScaleText(canvas);

        //这里是为了演示动画方便，实际开发中进度只会增加
        if(progressCount!= progress){
            if(progressCount< progress){
                progressCount++;
            }else{
                progressCount--;
            }
            postInvalidate();
        }
        super.onDraw(canvas);

    }

    /**
     * 绘制背景
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        //先画一个矩形
        RectF rectF = new RectF(100 ,100 ,500 ,500);
        //创建画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //添加画笔颜色
        paint.setColor(Color.parseColor("#FF4081"));
        canvas.drawRoundRect(rectF , 30 ,30 , paint);
    }

    /**
     * 绘制进度条
     * @param canvas
     */
    private void drawFg(Canvas canvas) {
        if (ratio == 0) {
            return;
        }
        float fgSideWidth= (sideWidth * 3);
        float fgSideTop = fgSideWidth + mProgressHintHeight;
        RectF fgRectF = new RectF(fgSideWidth + paddingWidth, fgSideTop, width - fgSideWidth - paddingWidth, progressHeight - fgSideWidth);
        Bitmap fgBitmap = Bitmap.createBitmap(width, progressHeight, Bitmap.Config.ARGB_8888);
        Canvas fgCanvas = new Canvas(fgBitmap);
//        if (fgSrc == null) {
//            fgSrc = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_progress);
//        }
        float fgX;
        float maxFgX = width - paddingWidth - fgSideWidth;
        if (progressCount > 0 && progressCount < max) {
            fgX = scaleRatio * (width - paddingWidth*2) * ratio + paddingWidth;
            fgX = (fgX >= maxFgX) ? maxFgX - fgSideWidth : fgX;
        } else if (progressCount == max){
            // 最大值的位置
            fgX = maxFgX;
        } else {
            // 0 的位置
            fgX = paddingWidth;
        }

        RectF rectF = new RectF(fgSideWidth + paddingWidth, fgSideTop, fgX, progressHeight - fgSideWidth);
        fgCanvas.drawRoundRect(rectF, progressRadius, progressRadius, srcPaint);

        srcPaint.setXfermode(mPorterDuffXfermode);
//        fgCanvas.drawBitmap(fgSrc, null, fgRectF, srcPaint);

        canvas.drawBitmap(fgBitmap, 0, 0, null);
        srcPaint.setXfermode(null);
    }

    /**
     * 绘制进入文字
     */
    private void drawProgressText (Canvas canvas) {
        float bgWidth;
        float maxBgWidth = width - paddingWidth - sideWidth * 3 - mProgressHintWidth/2;
        if (progressCount > 0 && progressCount < max) {
            bgWidth = scaleRatio * (width - paddingWidth*2)  * ratio - mProgressHintWidth/2  + paddingWidth + sideWidth;
            bgWidth = (bgWidth >= maxBgWidth) ? maxBgWidth - sideWidth * 3 : bgWidth;
        } else if (progressCount == 0){
            bgWidth = paddingWidth - mProgressHintWidth/2;
        } else {
            bgWidth = width - paddingWidth - sideWidth * 3 - mProgressHintWidth/2;
        }

//        canvas.drawBitmap(textSrc,  bgWidth, 0, srcPaint);

        float textWidth = textPaint.measureText(progress+"");
        //计算数值文字X坐标
        float textX = bgWidth + (mProgressHintWidth - textWidth) / 2;
        float textY = mProgressHintHeight/2 + 5;
        textPaint.setColor(textColor);
        canvas.drawText(progress+"", textX, textY, textPaint);
    }


    /**
     * 绘制刻度文字
     */
    private void drawProgressScaleText (Canvas canvas) {
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(scaleSize);
        // 刻度总个数
        float scaleCount = scaleList.size();
        for (int i = 0; i < scaleCount; i++) {
            float value = scaleList.get(i);
            float ratio = 0;
            if (value > 0 && max > 0) {
                ratio = (float) (Math.log(value) / Math.log(max));
            }
            float textWidth = textPaint.measureText(value+"");

            float textX = 0;
            if (value > 0 && value < max) {
                textX = (float)(i + 1)/scaleCount * (width - paddingWidth*2) * ratio + paddingWidth;
                RectF rectF = new RectF(textX, progressHeight - sideWidth, textX + 10, progressHeight - sideWidth + 24);
                Path path = new Path();
                path.addRoundRect(rectF, radiusArray, Path.Direction.CW);
                canvas.drawPath(path, sidePaint);
            }
            textX = textX - textWidth/2 + 10;
            float textY = progressHeight + 15;
            if (value == 0) {
                canvas.drawText(value+"", paddingWidth,  textY + scaleBottomNumTop, textPaint);
            } else if (value == max) {
                canvas.drawText(value+"", width - textWidth - paddingWidth,  textY + scaleBottomNumTop, textPaint);
            } else {
                canvas.drawText(value+"", textX,  textY + scaleBottomNumTop, textPaint);
            }
        }
    }

    /**
     * 设置四个角的圆角半径
     */
    public void setRadius(float leftTop, float rightTop, float rightBottom, float leftBottom) {
        radiusArray[0] = leftTop;
        radiusArray[1] = leftTop;
        radiusArray[2] = rightTop;
        radiusArray[3] = rightTop;
        radiusArray[4] = rightBottom;
        radiusArray[5] = rightBottom;
        radiusArray[6] = leftBottom;
        radiusArray[7] = leftBottom;
    }

    private int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 设置最大值，和初始进度值
     * @param max 最大值
     * @param progress 开始的进度值
     */
    public void setMax(float max, float progress) {
        this.max = max;
        this.progress = progress;
        postInvalidate();
    }

    /**
     * 设置进度
     * @param progress 当前进度
     */
    public void setProgress (float progress) {
        this.progress = progress;
        postInvalidate();
    }

    /**
     * 设置最大值，和初始进度值， 设置刻度
     * @param max 最大值
     * @param progress 开始的进度值
     */
    public void setMax(float max, float progress, List<Float> scaleList) {
        this.max = max;
        this.progress = progress > max ? max : progress;
        this.scaleList.clear();
        // 添加最大值和最小值刻度
        scaleList.add(0, 0f);
        scaleList.add(max);
        this.scaleList.addAll(scaleList);
        Collections.sort(this.scaleList);
        postInvalidate();
    }

    /**
     * 设置刻度
     * @param scaleList
     */
    public void setScaleList (List<Float> scaleList) {
        this.scaleList.clear();
        // 添加最大值和最小值刻度
        scaleList.add(0, 0f);
        scaleList.add(max);
        this.scaleList.addAll(scaleList);
        Collections.sort(this.scaleList);
        postInvalidate();
    }
}
