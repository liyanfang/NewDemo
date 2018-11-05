package cn.suanzi.newdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.Util;

/**
 * Created by liyanfang on 2018/4/12.
 * 描述：动态画图
 */
public class MyProgressView extends View{
    private static final String TAG = MyProgressView.class.getSimpleName();
    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingDefaultPaint;
    // 已用环的画笔
    private Paint mUsePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画白线的画笔
    private Paint mLinePaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private int mProgress;
    // 实际展示总进度
    private int mShowProgress;
    // 已用流量
    private String usedFlow;

    private Context mContext;

    private Handler circleHandler = new Handler(){

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                int temp = (Integer)msg.obj;
                setProgress(temp);
            }
        };
    };

    public MyProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);

        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    private void initVariable() {
        //画圆画笔设置
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);//防锯齿
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        //“使用”字画笔设置
        mUsePaint = new Paint();
        mUsePaint.setAntiAlias(true);
        mUsePaint.setStyle(Paint.Style.FILL);
        mUsePaint.setColor(getResources().getColor(R.color.ticket_color));
        mUsePaint.setTextSize(Util.sp2px(mContext, 10));

        //圆环画笔设置
        mRingDefaultPaint = new Paint();
        mRingDefaultPaint.setAntiAlias(true);
        mRingDefaultPaint.setColor(getResources().getColor(R.color.default_ring_color));
        mRingDefaultPaint.setStyle(Paint.Style.STROKE);
        mRingDefaultPaint.setStrokeWidth(mStrokeWidth);

        //已使用多少圆环画笔设置
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(Util.sp2px(mContext, 22));

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.WHITE);


        //获取字体高度
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        //画圆
        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);

        RectF oval = new RectF();
        oval.left = (mXCenter - mRingRadius);
        oval.top = (mYCenter - mRingRadius);
        oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
        oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
        //画整圆弧
        canvas.drawArc(oval, -90, 360, false, mRingDefaultPaint);
        //已使用多少圆弧
        Log.d(TAG, "MyProgressView  mProgress:  " + mProgress + "， mTotalProgress：" + mTotalProgress);
        canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint);
        //文字绘制
        String txt = mProgress + "%";
        //文字的长度
        mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
        canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 9, mTextPaint);

        Rect _pb = new Rect();
        String sup = "已用";
        mUsePaint.getTextBounds(sup, 0, sup.length(), _pb);
        int perX = mXCenter - _pb.width() / 2;
        canvas.drawText(sup, perX, mYCenter / 2, mUsePaint);

        if (!TextUtils.isEmpty(usedFlow)) {
            mUsePaint.getTextBounds(usedFlow, 0, usedFlow.length(), _pb);
            perX = mXCenter - _pb.width() / 2;
            canvas.drawText(usedFlow, perX, (float) (mYCenter + mYCenter / 1.7), mUsePaint);
        }

        // 画横线图片 TODO
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_bottom_bg);
//        perX = mXCenter - bitmap.getWidth() / 2;
//        canvas.drawBitmap(bitmap, perX, (float) (mYCenter + mYCenter / 5), mLinePaint);
    }

    /**
     * 设置当前进度
     * @param progress
     */
    public void setProgress(int progress) {
        mProgress = progress;
        Log.d(TAG, "MyProgressView setProgress:" + mProgress);
        postInvalidate();
    }

    /**
     * 实际展示总进度
     * @param progress
     */
    public void setmShowProgress(int progress) {
        mShowProgress = progress;
        new Thread(new CircleThread()).start();
    }

    public void setUsedFlow(String usedFlow) {
        this.usedFlow = usedFlow;
    }


    private class CircleThread implements Runnable{

        int m=0;
        int i=0;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(50);
                    m++;
                    Message msg = new Message();
                    msg.what = 1;
                    if(i < mShowProgress){
                        i += m;
                    }else{
                        i = mShowProgress;
                        return;
                    }
                    msg.obj = i;
                    circleHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
