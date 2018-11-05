package cn.suanzi.newdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import cn.suanzi.newdemo.R;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * @author liyanfang
 */
@SuppressLint("DrawAllocation")
public class IndicateProgressView01 extends View {

	/** 进度条背景颜色*/
	private int backgroundColor = 0xffFF7ABF;
	/** 进度开始颜色*/
	private int startProgressColor = 0xfff29310;
	/** 进度结束颜色*/
	private int endProgressColor = 0xffFFD54D;
	/** 文字颜色*/
	private int indicateTextColor = 0xffffffff;
	/** 进度条四个角的角度px*/
	private float radius = 20;
	/** 进度文字左边的距离*/
	private int progressTextRight = dp2px(5);
	private int progressTextTop = dp2px(2);
	/** 进度条最大值*/
	private int max = 100;
	/** 进度值*/
	private int progress;
	/** 进度值文字大小*/
	private float indicateTextSize = 32;
	/** 进度值*/
	private String indicateText;
	/** 进度背景话笔*/
	private Paint backPaint;
	/** 进度条背景画笔*/
	private Paint progressPaint;
	/** 描述进度文字画笔*/
	private Paint indicateTextPaint;

	public IndicateProgressView01(Context context) {
		super(context);
	}

	public IndicateProgressView01(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context, attributeSet);
	}
	
	private void init(Context context, AttributeSet attributeSet) {
//		TypedArray taArray = context.obtainStyledAttributes(attributeSet, R.styleable.IndicateProgressView);
//		if (taArray != null) {
//			backgroundColor = taArray.getColor(R.styleable.IndicateProgressView_backgroundColor, backgroundColor);
//			startProgressColor = taArray.getColor(R.styleable.IndicateProgressView_startProgressColor, startProgressColor);
//			endProgressColor = taArray.getColor(R.styleable.IndicateProgressView_endProgressColor, endProgressColor);
//			indicateTextColor = taArray.getColor(R.styleable.IndicateProgressView_indicateTextcolor, indicateTextColor);
//			radius = taArray.getDimension(R.styleable.IndicateProgressView_indicateRadius, radius);
//			indicateTextSize = taArray.getDimension(R.styleable.IndicateProgressView_indicateSize, indicateTextSize);
//			taArray.recycle();
//		}
		//初始化进度条背景画笔
		backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backPaint.setColor(backgroundColor);
		backPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		//初始化进度条进度画笔
		progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		progressPaint.setStyle(Paint.Style.FILL);
		//初始化进度条指示器文本画笔
		indicateTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		indicateTextPaint.setColor(indicateTextColor);
		indicateTextPaint.setTextSize(indicateTextSize);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		
		//画背景
		 RectF backRectF = new RectF(0, 0, width, height);
		backPaint.setColor(backgroundColor);
		canvas.drawRoundRect(backRectF, radius, radius, backPaint);
		
		// 画进度
		RectF progressRectF = new RectF(0, 0, width * getScale(), height);
		LinearGradient lGradient = new LinearGradient(0, 0, width * getScale(), height,
				startProgressColor, endProgressColor, Shader.TileMode.MIRROR);
		progressPaint.setShader(lGradient);
		canvas.drawRoundRect(progressRectF, radius, radius, progressPaint);
		// 进度值
		float textX = progressRectF.right - indicateTextPaint.measureText(indicateText) - progressTextRight;
		float textY = height - progressTextTop;
		canvas.drawText(indicateText, textX, textY, indicateTextPaint);
	}

	@Override
	public void setBackgroundColor(int color) {
		this.backgroundColor = color;
		backPaint.setColor(backgroundColor);
		postInvalidate();
	}
	
	public void setStartProgressColor(int color) {
		this.startProgressColor = color;
		postInvalidate();
	}
	
	public void setEndProgressColor(int color) {
		this.endProgressColor = color;
		postInvalidate();
	}
	
	public void setIndicateTextColor(int color) {
		this.indicateTextColor = color;
		indicateTextPaint.setColor(indicateTextColor);
		postInvalidate();
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public void setProgress(int progress) {
		this.progress = progress;
		postInvalidate();
	}
	
	private float getScale() {
		float scale;
		if (max == 0) {
			scale = 0;
		} else {
			scale = (float)progress / (float)max;
		}
		setIndicateText((int)(scale * 100) +"");
		return scale;
	}
	
	private void setIndicateText(String indicateStr) {
		this.indicateText = indicateStr;
	}

}
