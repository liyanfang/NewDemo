package cn.suanzi.newdemo.view.progress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.suanzi.newdemo.R;

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
	/** 进度值*/
	private int progress;
	/** 进度值文字大小*/
	private float topTextSize = sp2px(11);
	/** 底部文字大小*/
	private float bottomTextSize = sp2px(13);
	/** 刻度文字画笔*/
	private Paint paint;
	private Paint roundPaint;
	private Paint bottomTextPaint;
	/** 进度矩形*/
	private RectF progressRectF = new RectF();

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
	/** 刻度对应的文字描述*/
	private String startTopText;
	private String startBottomText;
	/** 刻度对应的文字描述的颜色*/
	private int startDesTitleColor = 0xffFF9CCE;
	/** 上下刻度颜色*/
	private int scaleColor = 0xffFFDD00;
	/** 刻度圆默认颜色*/
	private int scaleCircleColor = 0xffF76B1C;
	/**刻度圆选中颜色*/
	private int scaleCircleSelectColor = 0xffAC0BA0;

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
		// 初始画笔
		roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		bottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(backgroundColor);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		this.startTopText = "销售额";
		this.startBottomText = "奖励金";
		addScaleList();
	}

	/**
	 * 刻度，demo里面测写的默认
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
		if (scaleCount <= 1) {
			// 刻度小余1不需要处理
			return;
		}
		float distance = ((mWidth - 2 * paddingLeft) / (scaleCount - 1));
		float centerY = mHeight / 2;
		// 计算文字Y位置
		float topTextY = centerY - mRoundRadius - dp2px(8);
		float bottomTextY = centerY + mRoundRadius + dp2px(13 + 7);
		drawProgress(canvas, distance);
		// 画进度
		for (int i = 0; i < scaleCount; i++) {
			TeamProgress teamProgress = salesList.get(i);
			// 画园
			roundPaint.setStyle(Paint.Style.FILL);
			// 上面的刻度文字
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(Color.WHITE);
			paint.setTextSize(topTextSize);
			// 下面奖励刻度文字
			bottomTextPaint.setColor(Color.WHITE);
			bottomTextPaint.setTextSize(bottomTextSize);
			// 设置超过进度圆圈颜色值
			if (progress >= teamProgress.getSale()) {
				roundPaint.setColor(scaleCircleColor);
				paint.setColor(scaleColor);
				bottomTextPaint.setColor(scaleColor);
			} else {
				roundPaint.setColor(scaleCircleSelectColor);
				paint.setColor(Color.WHITE);
				bottomTextPaint.setColor(Color.WHITE);
			}
			float distanceX = distance*i;
			canvas.drawCircle(distanceX + paddingLeft, centerY, mRoundRadius, roundPaint);
			if (i == 0) {
				bottomTextPaint.setTextSize(topTextSize);
				paint.setColor(startDesTitleColor);
				bottomTextPaint.setColor(startDesTitleColor);
				// 测量文字的宽
				float topTextWidth = paint.measureText(startTopText);
				float bottomTextWidth = bottomTextPaint.measureText(startBottomText);
				// 计算文字X轴位置
				float topTextX = distanceX + paddingLeft - topTextWidth/2;
				float bottomTextX = distanceX + paddingLeft - bottomTextWidth/2;

				canvas.drawText(startTopText, topTextX, topTextY, paint);
				canvas.drawText(startBottomText, bottomTextX, bottomTextY, bottomTextPaint);

			} else {

				// 测量文字的宽
				float topTextWidth = paint.measureText(teamProgress.getSaleText());
				float bottomTextWidth = bottomTextPaint.measureText(teamProgress.getRewardText());
				// 计算文字X轴位置
				float topTextX = distanceX + paddingLeft - topTextWidth/2;
				float bottomTextX = distanceX + paddingLeft - bottomTextWidth/2;

				canvas.drawText(teamProgress.getSaleText(), topTextX, topTextY, paint);
				canvas.drawText(teamProgress.getRewardText(), bottomTextX , bottomTextY, bottomTextPaint);
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
		progressRectF.set(paddingLeft + mRoundRadius /2, top, mWidth - paddingLeft, bottom);
		// 初始化进度条背景画笔
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(backgroundColor);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRoundRect(progressRectF, radius, radius, paint);
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
		}

		// 进度左边的x轴位置
		float top =  (mHeight / 2 - progressHeight/2);
		float bottom =  (mHeight / 2 + progressHeight/2);
		float maxRightDistance = scaleCount * distance + paddingLeft;
		float rightDistance = (position - 1) * distance + distance * scale + paddingLeft;
		rightDistance = rightDistance > maxRightDistance ? maxRightDistance : rightDistance;

		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(startProgressColor);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		progressRectF.set(paddingLeft + mRoundRadius /2, top, rightDistance, bottom);
		canvas.drawRoundRect(progressRectF, radius, radius, paint);

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

	public int dp2px(float dpValue) {
		return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
	}

	@Override
	public void setBackgroundColor(int color) {
		this.backgroundColor = color;
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
		Collections.sort(salesList, (progress, progress1) -> {
			// 返回值 小于0正序，大于0倒序
			return progress.getSale() - progress1.getSale();
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

	public class TeamProgress {

		/** 销售额*/
		private int sale;
		/** 奖励*/
		private int reward;
		/** 刻度对应的字符串*/
		private String saleText;
		private String rewardText;

		public TeamProgress(int sale, int reward) {
			this.sale = sale;
			this.reward = reward;
			this.saleText = String.valueOf(sale);
			this.rewardText = String.valueOf(reward);
		}

		public int getSale() {
			return sale;
		}

		public void setSale(int sale) {
			this.sale = sale;
			this.saleText = String.valueOf(sale);
		}

		public int getReward() {
			return reward;
		}

		public void setReward(int reward) {
			this.reward = reward;
			this.rewardText = String.valueOf(reward);
		}

		public String getSaleText() {
			return saleText;
		}

		public void setSaleText(String saleText) {
			this.saleText = saleText;
		}

		public String getRewardText() {
			return rewardText;
		}

		public void setRewardText(String rewardText) {
			this.rewardText = rewardText;
		}
	}
	
}
