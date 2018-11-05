package cn.suanzi.newdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.pojo.TeamProgress;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * @author liyanfang
 */
@SuppressLint("DrawAllocation")
public class TeamProgressView extends View {

	/** 进度条背景颜色*/
	private int backgroundColor = 0xffFF7ABF;
	/** 进度开始颜色*/
	private int startProgressColor = 0xfff29310;
	/** 进度结束颜色*/
	private int endProgressColor = 0xffFFD54D;
	/** 文字颜色*/
	private int textColor = 0xffffffff;
	/** 进度条四个角的角度px*/
	private float radius = 20;
//	/** 进度条最大值*/
//	private int max = 100;
	/** 进度值*/
	private int progress;
	/** 进度值文字大小*/
	private float topTextSize = sp2px(11);
	/** 底部文字大小*/
	private float bottomTextSize = sp2px(13);
	/** 进度背景画笔*/
	private Paint backPaint;
	/** 进度条背景画笔*/
	private Paint progressPaint;
	/** 刻度文字画笔*/
	private Paint topTextPaint;
	/** 刻度奖励文字画笔*/
	private Paint bottomTextPaint;
	/** 进度背景*/
	private RectF progressRectF;

	/** 销售额/奖励金额*/
	private List<TeamProgress> salesList = new ArrayList<>();
	/** 刻度园的半径*/
	private int mRoundRadius = dp2px(6);
	/** 左右边距*/
	private float paddingLeft = dp2px(16);
	/** 进度条高度*/
	private float progressHeight = dp2px(7);
	/** 控件的宽高*/
	private float mHeight;
	private float mWidth;

	public TeamProgressView(Context context) {
		super(context);
	}
	
	public TeamProgressView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context, attributeSet);
	}
	
	private void init(Context context, AttributeSet attributeSet) {
		TypedArray taArray = context.obtainStyledAttributes(attributeSet, R.styleable.TeamProgressView);
		if (taArray != null) {
			backgroundColor = taArray.getColor(R.styleable.TeamProgressView_teamBackgroundColor, backgroundColor);
			startProgressColor = taArray.getColor(R.styleable.TeamProgressView_teamProgressColor, startProgressColor);
			endProgressColor = taArray.getColor(R.styleable.TeamProgressView_teamProgressColor, endProgressColor);
			textColor = taArray.getColor(R.styleable.TeamProgressView_teamTextcolor, textColor);
			radius = taArray.getDimension(R.styleable.TeamProgressView_teamRadius, radius);
			topTextSize = taArray.getDimension(R.styleable.TeamProgressView_teamTopTextSize, topTextSize);
			bottomTextSize = taArray.getDimension(R.styleable.TeamProgressView_teamBottomTextSize, bottomTextSize);
			taArray.recycle();
		}
		// 初始化进度条背景画笔
		backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backPaint.setColor(backgroundColor);
		backPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		// 初始化进度条进度画笔
		progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		progressPaint.setColor(startProgressColor);
		progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		addScaleList();
	}

	/**
	 * 刻度
	 */
	private void addScaleList () {
		salesList.add(new TeamProgress(0,0));
		salesList.add(new TeamProgress(1,100));
		salesList.add(new TeamProgress(10,200));
		salesList.add(new TeamProgress(20,300));
		salesList.add(new TeamProgress(300,400));
		salesList.add(new TeamProgress(800,500));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		
		//画背景
		drawBg(canvas);
		// 进度值
		drawProgressScaleText(canvas);
	}

	/**
	 * 绘制刻度文字
	 */
	private void drawProgressScaleText (Canvas canvas) {

		// 刻度总个数
		int scaleCount = salesList.size();
		float distance = ((mWidth - 2 * paddingLeft) / (scaleCount - 1));
		float centerY = mHeight / 2;
		// 计算文字Y位置
		float topTextY = centerY - mRoundRadius - dp2px(8);
		float bottomTextY = centerY + mRoundRadius + dp2px(13 + 7);
		drawProgress(canvas, distance);
		// 画进度
		for (int i = 0; i < scaleCount; i++) {
			TeamProgress teamProgress = salesList.get(i);
			Paint roundPaint = new Paint();
			// 画园
			roundPaint.setStyle(Paint.Style.FILL);
			// 上面的刻度文字
			topTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			topTextPaint.setColor(Color.WHITE);
			topTextPaint.setTextSize(topTextSize);
			// 下面奖励刻度文字
			bottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			bottomTextPaint.setColor(Color.WHITE);
			bottomTextPaint.setTextSize(bottomTextSize);
			// 设置超过进度圆圈颜色值
			if (progress >= teamProgress.getSale()) {
				roundPaint.setColor(Color.parseColor("#F76B1C"));
				topTextPaint.setColor(Color.parseColor("#FFDD00"));
				bottomTextPaint.setColor(Color.parseColor("#FFDD00"));
			} else {
				roundPaint.setColor(Color.parseColor("#AC0BA0"));
				topTextPaint.setColor(Color.WHITE);
				bottomTextPaint.setColor(Color.WHITE);
			}
			float distanceX = distance*i;
			canvas.drawCircle(distanceX + paddingLeft, centerY, mRoundRadius, roundPaint);
			if (i == 0) {
				bottomTextPaint.setTextSize(topTextSize);
				topTextPaint.setColor(Color.parseColor("#FF9CCE"));
				bottomTextPaint.setColor(Color.parseColor("#FF9CCE"));
				// 测量文字的宽
				float topTextWidth = topTextPaint.measureText("销售额");
				float bottomTextWidth = bottomTextPaint.measureText("销售额");
				// 计算文字X轴位置
				float topTextX = distanceX + paddingLeft - topTextWidth/2;
				float bottomTextX = distanceX + paddingLeft - bottomTextWidth/2;

				canvas.drawText("销售额", topTextX, topTextY, topTextPaint);
				canvas.drawText("奖励金", bottomTextX, bottomTextY, bottomTextPaint);

			} else {

				// 测量文字的宽
				float topTextWidth = topTextPaint.measureText(teamProgress.getSale()+"");
				float bottomTextWidth = bottomTextPaint.measureText(teamProgress.getReward()+"");
				// 计算文字X轴位置
				float topTextX = distanceX + paddingLeft - topTextWidth/2;
				float bottomTextX = distanceX + paddingLeft - bottomTextWidth/2;

				canvas.drawText(teamProgress.getSale()+"", topTextX, topTextY, topTextPaint);
				canvas.drawText(teamProgress.getReward()+"", bottomTextX , bottomTextY, bottomTextPaint);
			}
		}
	}

	/**
	 * 画背景
	 * @param canvas
	 */
	private void drawBg (Canvas canvas) {
		//画背景
		float top =  (mHeight / 2 - progressHeight/2);
		float bottom =  (mHeight / 2 + progressHeight/2);
		RectF backRectF = new RectF(paddingLeft + mRoundRadius /2, top, mWidth - paddingLeft, bottom);
		backPaint.setColor(backgroundColor);
		canvas.drawRoundRect(backRectF, radius, radius, backPaint);
	}

	/**
	 * 画进度
	 * @param canvas
	 * @return 进度条所在的段位
	 */
	private void drawProgress (Canvas canvas, float distance) {
		if (progress == 0) {
			return;
		}
		// 进度条所在的段位
		int position = 0;
		int scaleValue = 0;
		for (int i = 0; i < salesList.size(); i++) {
			TeamProgress teamProgress = salesList.get(i);
			scaleValue = teamProgress.getSale();
			if (progress <= scaleValue) {
				position = i;
				break;
			}
		}
		// 总共有多少接
		int scaleCount = salesList.size() - 1;
		// 判断当前进度超过最大进度
		if (progress > scaleValue) {
			position = scaleCount;
		}
		if (position == 0) {
			return;
		}
		TeamProgress teamProgress = salesList.get(position - 1);
		float previousValue = teamProgress.getSale();

		float scale;
		if (progress > scaleValue) {
			scale = 1;
		} else {
			scale = (progress - previousValue)/(scaleValue - previousValue);
//			scale = progress/scaleValue;
		}

		// 进度左边的x轴位置
		float top =  (mHeight / 2 - progressHeight/2);
		float bottom =  (mHeight / 2 + progressHeight/2);
		float maxRightDistance = scaleCount * distance + paddingLeft;
		float rightDistance = (position - 1) * distance + distance * scale + paddingLeft;
		rightDistance = rightDistance > maxRightDistance ? maxRightDistance : rightDistance;

		progressRectF = new RectF(paddingLeft + mRoundRadius /2, top, rightDistance, bottom);
		canvas.drawRoundRect(progressRectF, radius, radius, progressPaint);

	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * @param spValue （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	private int sp2px(float spValue) {
		final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	@Override
	public void setBackgroundColor(int color) {
		this.backgroundColor = color;
		backPaint.setColor(backgroundColor);
		postInvalidate();
	}

	/**
	 * 设置进度
	 * @param progress 当前进度
	 */
	public void setProgress (int progress) {
		this.progress = progress;
		postInvalidate();
	}

	/**
	 * 设置最大值，和初始进度值， 设置刻度
	 * @param max 最大值
	 * @param progress 开始的进度值
	 */
	public void setMax(int max, int progress, List<TeamProgress> salesList) {
		this.progress = progress;
//		if (!ListUtil.isEmpty(salesList)) {
			this.salesList.clear();
			this.salesList.addAll(salesList);
			sort(salesList);
//		}
		postInvalidate();
	}


	/**
	 * 设置最大值，和初始进度值， 设置刻度
	 * @param progress 开始的进度值
	 */
	public void setProgress(int progress, List<TeamProgress> salesList) {
		this.progress = progress;
//		if (!ListUtil.isEmpty(salesList)) {
			this.salesList.clear();
			this.salesList.addAll(salesList);
			sort(salesList);
//		}
		postInvalidate();
	}

	/**
	 * 设置刻度
	 * @param salesList
	 */
	public void setSalesList (List<TeamProgress> salesList) {
//		if (!ListUtil.isEmpty(salesList)) {
			this.salesList.clear();
			// 添加最大值和最小值刻度
			this.salesList.addAll(salesList);
			sort(salesList);
			postInvalidate();

//		}
	}

    /**
     * 倒叙排列
	 * @param salesList
	 */
	public void sort (List<TeamProgress> salesList) {
		Collections.sort(salesList, new Comparator<TeamProgress>() {
			@Override
			@SuppressWarnings("unchecked")
			public int compare(TeamProgress progress, TeamProgress progress1) {
				// 返回值 小于0正序，大于0倒序
				return progress.getSale() - progress1.getSale();
			}
		});
	}

	/**
	 * 进度
	 * @return
	 */
	private float getScale(int maxValue) {
		float scale;
		if (progress > maxValue) {
			return 1;
		}
		if (maxValue == 0) {
			scale = 0;
		} else {
			scale = (float)progress / (float)maxValue;
		}
		return scale;
	}
	
}
